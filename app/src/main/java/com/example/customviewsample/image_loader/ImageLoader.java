package com.example.customviewsample.image_loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.customviewsample.R;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.HttpsURLConnection;

public class ImageLoader{


    private static final String TAG = ImageLoader.class.getSimpleName();
    private static final int TAG_KEY_URI = R.id.imageloader_uri;
    private static final int MESSAGE_POST_RESULT = 10000;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private boolean mIsDiskLruCacheCreated = false;
    private int DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private int DISK_CACHE_INDEX = 0;
    private ImageResizer mImageRezizer = new ImageResizer();



    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
            String uri = (String) imageView.getTag(TAG_KEY_URI);
            if (uri.equals(result.uri)){
                imageView.setImageBitmap(result.bitmap);
            }else {
                Log.d(TAG,"set image bitmap,but url has changed,ignored!");
            }
        }
    };


    public ImageLoader(Context context) {
        mContext = context.getApplicationContext();
        //这里ImageLoader的内存缓存的容量为当前进程可用内存的1/8,
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };

        //磁盘缓存的容量为50MB
        File diskCacheDir = getDiskCacheDir(mContext,"bitmap");
        if (!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        //添加判断，可能磁盘剩余空间小于所需磁盘的大小，一般是指用户的手机空间不足了，因此无法创建磁盘缓存，这个时候磁盘缓存就会失效
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE){
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * build a new instance of ImageLoader
     * @param context
     * @return
     */
    public static ImageLoader build(Context context){
        return new ImageLoader(context);
    }



    private long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }
        final StatFs statFs = new StatFs(path.getPath());
        return (long)statFs.getBlockSize() * (long)statFs.getAvailableBlocks();
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getExternalCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }



    /**
     * 添加内存缓存
     * @param key
     * @param bitmap
     */
    private void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if (getBitmapFromMemCache(key) == null){
            mMemoryCache.put(key,bitmap);
        }
    }


    /**
     * 获取内存缓存
     * @param key
     * @return
     */
    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 从网络获取图片
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     * @throws IOException
     */
    private Bitmap loadBitmapFromHttp(String url,int reqWidth,int reqHeight) throws IOException{
//        Log.d(TAG,"loadBitmapFromHttp()" + " url = " + url);
        if(Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("can not visit network form UI Thread.");
        }
        if (mDiskLruCache == null){
            return null;
        }
        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(url,outputStream)){
                editor.commit();
            }else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url,reqWidth,reqHeight);
    }

    /**
     * 从磁盘获取缓存
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()){
            Log.w(TAG,"load bitmap from UI Thread,it's not recommended!");
        }
        if (mDiskLruCache == null){
            return null;
        }
        Bitmap bitmap = null;
        String key = hashKeyFromUrl(url);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null){
                FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                FileDescriptor fileDescriptor = fileInputStream.getFD();
                bitmap = mImageRezizer.decodeSampledBitmapFromFileDescriptor(fileDescriptor,reqWidth,reqHeight);
                if (bitmap != null){
                    addBitmapToMemoryCache(key,bitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }




    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            Log.d(TAG,"url = " + url + " in = " + in.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            Log.d(TAG,"url = " + url + " in = " + in.toString());
            out = new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);
            int b;
            while ((b = in.read()) != -1){
                out.write(b);
            }
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e(TAG,"downloadBitmap failed." + e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            MyUtils.close(out);
            MyUtils.close(in);
        }
        return false;
    }



    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            cacheKey = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < bytes.length;i ++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * 同步加载，获取图片
     * @param uri
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap loadBitmap(String uri,int reqWidth,int reqHeight){
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        //首先尝试从内存缓存读取图片
        if (bitmap != null){
            Log.d(TAG,"loadBitmapFromMemCache,url:" + uri);
            return bitmap;
        }
        //接着尝试从磁盘缓存中读取图片
        bitmap = loadBitmapFromDiskCache(uri,reqWidth,reqHeight);
        if (bitmap != null){
            Log.d(TAG,"loadBitmapFromDisk,url:" + uri);
            return bitmap;
        }
        //最后从网络中拉取图片
        try {
            bitmap = loadBitmapFromHttp(uri,reqWidth,reqHeight);
            Log.d(TAG,"loadBitmapFromHttp,url:" + uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null && !mIsDiskLruCacheCreated){
            Log.d(TAG,"encounter error,DiskLruCache is not created.");
            bitmap = downloadBitmapFromUrl(uri);
        }
        return bitmap;

    }

    /**
     * load bitmap form memory cache or disk cache or network async, then bind imageView and bitmap.
     * NOTE THAT:should run in UI Thread
     * @param uri
     * @param imageView
     */
    public void bindBitmap(final String uri,final ImageView imageView){
        bindBitmap(uri,imageView,0,0);
    }

    /**

     * @param uri
     * @param imageView
     * @param reqWidth
     * @param reqHeight
     */
    public void bindBitmap(final String uri, final ImageView imageView,final int reqWidth,final int reqHeight){

        imageView.setTag(TAG_KEY_URI,uri);
        //尝试从内存缓存中读取图片，如果读取成功直接返回结果
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        //否则在线程池中调用loadBitmap方法
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri,reqWidth,reqHeight);
                if (bitmap != null){
                    //当图片加载成功后，将图片、图片的地址以及需要绑定的imageView封装成一个LoaderResult对象
                    LoaderResult result = new LoaderResult(imageView,uri,bitmap);
                    //再通过mMainHandler向主线程发送一个消息，这样就可以在主线中给imageView设置图片了。
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT,result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        try {
            final  URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            MyUtils.close(in);
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromMemCache(String url) {
        final String key = hashKeyFromUrl(url);
        Bitmap bitmap = getBitmapFromMemCache(key);
        return bitmap;
    }



    private class LoaderResult {
        public ImageView imageView;
        public Bitmap bitmap;
        public String uri;

        public LoaderResult(ImageView imageView, String uri,Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;

        }
    }


    private static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"ImageLoader#" + mCount.getAndIncrement());
        }
    };

    /**
     * 这边创建线程池
     * 如果直接采用普通的线程去加载图片，随着列表的滑动这可能会产生大量的线程，这样并不利于整体效率的提升。
     * 这里没有采用AsyncTask，AsyncTask封装了线程池和Handler，按道理它应该适合ImageLoader的场景。
     * 在3.0以上的版本无法实现并发的效果，这里可以通过改造AsyncTask或者使用AsyncTask的executeOnExecutor方法的形式来执行异步任务。但是是不太自然的实现方式。
     */
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(),sThreadFactory);
}
