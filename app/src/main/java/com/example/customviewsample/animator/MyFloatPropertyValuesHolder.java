package com.example.customviewsample.animator;

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
//    MyKeyframeSet mKeyframes;
    Method mSetter = null;


    public MyFloatPropertyValuesHolder(String propertyName,float... values){
        mPropertyName = propertyName;
        mValueType = float.class;
    }


}
