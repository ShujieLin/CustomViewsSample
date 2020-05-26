package com.example.customviewsample.uitils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static String TAG = "CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public static CrashHandler sInstance = new CrashHandler();
    private Activity mContext;
    private Map<String,String> mInfos = new HashMap<>();
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //DCL双重锁单例模板方法
    private static CrashHandler mCrashHandler = null;
    private CrashHandler(){}
    public static CrashHandler getInstance(){
        if (mCrashHandler == null){
            synchronized (CrashHandler.class){
                if (mCrashHandler == null){
                    mCrashHandler = new CrashHandler();
                }
            }
        }
        return mCrashHandler;
    }

    public void init(Activity activity){
        mContext = activity;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        autoClear(5);
    }


    private void autoClear(int i) {
        // TODO: 2020/5/26
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        if (!handleException(e) && mDefaultHandler != null){
            //假如用户不处理则让默认系统mDefaultHandler处理；
            mDefaultHandler.uncaughtException(t,e);
        }else {
            //退出程序
            SystemClock.sleep(3000);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 处理错误信息
     * @param throwable
     * @return
     */
    private boolean handleException(Throwable throwable){
        if (throwable == null){
            return false;
        }

        try {
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext,"抱歉，程序出现异常，即将重启",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();

            //收集设备参数信息
            collectDeviceInfo(mContext);
            //保存日志文件
            saveCrashInfoFile(throwable);
            SystemClock.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 收集设备信息
     * @param context
     */
    private void collectDeviceInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),PackageManager.GET_ACTIVITIES);
            if (packageInfo != null){
                String versionName = packageInfo.versionName + " ";
                String versionConde = packageInfo.versionCode + " ";
                mInfos.put("versionName",versionName);
                mInfos.put("versionCode",versionConde);
            }


            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                mInfos.put(field.getName(),field.get(null).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String saveCrashInfoFile(Throwable throwable) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date());
            stringBuffer.append("\r\n" + date + "\n");
            for (Map.Entry<String,String>entry : mInfos.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                stringBuffer.append(key + " = " + value + "\n");
            }
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            Throwable throwableCase = throwable.getCause();
            while (throwableCase != null){
                throwableCase.printStackTrace(printWriter);
                throwableCase = throwableCase.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = stringWriter.toString();
            stringBuffer.append(result);

            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_PERMISSION_STORAGE = 100;
                String[] permissions = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                for (String str : permissions) {
                    if (mContext.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(mContext,permissions,REQUEST_CODE_PERMISSION_STORAGE);
                    }
                }
            }

            String fileName = writeFile(stringBuffer.toString());
            return fileName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 保存到file
     * 路径为该app安装路径
     * @param sb
     * @return
     * @throws Exception
     */
    private String writeFile(String sb) throws Exception {
        String time = mDateFormat.format(new Date());
        String fileName = "crash-" + time + ".log";
        String path = mContext.getPackageResourcePath();
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(path + fileName,true);
        fileOutputStream.flush();
        fileOutputStream.close();
        return fileName;
    }



}
