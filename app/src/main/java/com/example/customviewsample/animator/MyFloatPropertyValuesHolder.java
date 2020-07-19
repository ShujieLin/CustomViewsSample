package com.example.customviewsample.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyFloatPropertyValuesHolder {
    /**
     * 属性名
     */
    String mPropertyName;
    /**
     *
     */
    Class mValueType;
    MyKeyframeSet mKeyframes;
    Method mSetter = null;



    public MyFloatPropertyValuesHolder(String propertyName,float... values){
        mPropertyName = propertyName;
        mValueType = float.class;
        mKeyframes = MyKeyframeSet.ofFloat(values);
    }

    public void setupSetter(WeakReference<View> target){
        char firstLetter = Character.toLowerCase(mPropertyName.charAt(0));
        String theRest = mPropertyName.substring(1);
        String methodName = "set" + firstLetter + theRest;
        try {
            mSetter = View.class.getMethod(methodName,float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setAnimatedValue(View target,float fraction){
        Object value = mKeyframes.getValue(fraction);
        try {
            mSetter.invoke(target,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
