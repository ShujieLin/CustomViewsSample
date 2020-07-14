package com.example.customviewsample.image_loader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.customviewsample.R;

import java.util.ArrayList;
import java.util.List;

public class Main8Activity extends AppCompatActivity implements AbsListView.OnScrollListener {
    private static final String TAG = Main8Activity.class.getSimpleName();
    private List<String> mUrList = new ArrayList<>();
    private int mImageWidth;
    private boolean mIsWifi = false;
    private boolean mCanGetBitmapFromNetwork = false;
    private boolean mIsGridViewIdle = true;
    private ImageLoader mImageLoader;
    private ImageAdapter mImageAdapter;
    private GridView mImageGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        initData();
        initView();
        mImageLoader = ImageLoader.build(this);
    }

    private void initView() {
        mImageGridView = findViewById(R.id.gridView);
        mImageAdapter = new ImageAdapter(this);
        mImageGridView.setAdapter(mImageAdapter);
        mImageGridView.setOnScrollListener(this);

        if (!mIsWifi){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("初次使用会从网络下载图片，确定要下载吗？");
            builder.setTitle("注意");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCanGetBitmapFromNetwork = true;
                    mImageAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("否",null);
            builder.show();
        }
    }

    private void initData(){
        String[] imageUrls = {
                "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
                "http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg",
                "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
        };

        for (String url : imageUrls){
            mUrList.add(url);
        }

        int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        int space = (int) MyUtils.dp2px(this,20f);
        mImageWidth = (screenWidth - space) / 3;
        mIsWifi = MyUtils.isWifi(this);
        if (mIsWifi){
            mCanGetBitmapFromNetwork = true;
        }
    }


    private class ImageAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        private Drawable mDefaultBitmapDrawable;

        private ImageAdapter(Context context){
            mInflater = LayoutInflater.from(context);
            mDefaultBitmapDrawable = context.getResources().getDrawable(R.drawable.ic_launcher_background);

        }

        @Override
        public int getCount() {
            return mUrList.size();
        }

        @Override
        public String getItem(int position) {
            return mUrList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                convertView = mInflater.inflate(R.layout.image_list_item,parent,false);
                holder = new ViewHolder();
                holder.imageView = convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            ImageView imageView = holder.imageView;
            final String tag = (String) imageView.getTag();
            final String uri = getItem(position);
            if (!uri.equals(tag)){
                imageView.setImageDrawable(mDefaultBitmapDrawable);
            }
            if (mIsGridViewIdle && mCanGetBitmapFromNetwork){
                Log.d(TAG,"load uri : " + uri);
                imageView.setTag(uri);
                //通过bindBitmap方法，将复杂的图片加载过程交给了ImageLoader
                mImageLoader.bindBitmap(uri,imageView,mImageWidth,mImageWidth);
            }
             return convertView;
        }


        private class ViewHolder {
            public ImageView imageView;
        }
    }




    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当列表处于空闲状态（非滚动状态），进行刷新
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            mIsGridViewIdle = true;
            mImageAdapter.notifyDataSetChanged();
        }else {
            mIsGridViewIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

}
