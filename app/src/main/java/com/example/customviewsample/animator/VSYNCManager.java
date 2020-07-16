package com.example.customviewsample.animator;

import java.util.ArrayList;
import java.util.List;

public class VSYNCManager {

    private static final VSYNCManager outInstance = new VSYNCManager();

    public static VSYNCManager getInstance(){
        return outInstance;
    }

    private VSYNCManager(){
        new Thread(mRunnable).start();
    }

    public void add(AnimationFrameCallback animationFrameCallback){
        mAnimationFrameCallbackList.add(animationFrameCallback);
    }



    private List<AnimationFrameCallback> mAnimationFrameCallbackList = new ArrayList<>();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                try {
                    //60hz刷新率，16ms刷新一次
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (AnimationFrameCallback animationFrameCallback : mAnimationFrameCallbackList){
                    animationFrameCallback.doAnimationFrame(System.currentTimeMillis());
                }
            }
        }
    };

    /**
     * 观察者模式
     */
    interface AnimationFrameCallback{
        boolean doAnimationFrame(long currentTime);
    }
}
