package com.example.customviewsample.animator;

import android.view.View;

import java.lang.ref.WeakReference;

public class MyObjectAnimator  {

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

    public MyObjectAnimator(View view, String propertyName, float... value) {
        target = new WeakReference<>(view);
    }

    //    MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public static MyObjectAnimator ofFloat(View view,String propertyName,float... value){
        MyObjectAnimator animator = new MyObjectAnimator(view,propertyName,value);
        return animator;
    }

}
