package com.example.customviewsample.animator;

import android.view.View;

import java.lang.ref.WeakReference;

public class MyObjectAnimator  implements VSYNCManager.AnimationFrameCallback {

    private static final String TAG = "tuch";
    /**
     * 开始时间
     */
    long mStartTime = -1;

    /**
     * 动画持续时间
     */
    private long mDuration = 0;
    /**
     * 当前的view
     */
    private WeakReference<View> target;
    private float index = 0;
    private TimeInterpolator interpolator;

    MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;

    public MyObjectAnimator(View view, String propertyName, float... values) {
        target = new WeakReference<>(view);
        myFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName,values);
    }


    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public static MyObjectAnimator ofFloat(View view,String propertyName,float... value){

        MyObjectAnimator animator = new MyObjectAnimator(view,propertyName,value);
        return animator;
    }

    /**
     * 每个16ms回调一次
     * @param currentTime
     * @return
     */
    @Override
    public boolean doAnimationFrame(long currentTime) {
        return false;
    }



    public void start(){
        myFloatPropertyValuesHolder.setupSetter(target);
        mStartTime = System.currentTimeMillis();
        VSYNCManager.getInstance().add(this);
    }

}
