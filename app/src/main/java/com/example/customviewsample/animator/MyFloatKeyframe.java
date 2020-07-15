package com.example.customviewsample.animator;

/**
 * 关键帧
 */
public class MyFloatKeyframe {
    float mFraction;
    Class mValueType;
    float mValue;

    public MyFloatKeyframe(float fraction,float value){
        mFraction = fraction;
        mValue = value;
        mValueType = float.class;
    }

    public float getValue(){
        return mValue;
    }

    public void setValue(float value){
        mValue = value;
    }

    public float getFraction(){
        return mFraction;
    }


}
