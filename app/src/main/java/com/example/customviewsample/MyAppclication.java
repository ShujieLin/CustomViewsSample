package com.example.customviewsample;

import android.app.Application;

import com.example.customviewsample.uitils.CrashHandler;

public class MyAppclication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler.getInstance().init();
    }

}
