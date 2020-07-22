package com.example.customviewsample.layoutInflater_demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class MyInflater extends LayoutInflater {
    private LayoutInflater inflater;

    protected MyInflater(Context context) {
        super(context);
    }

    protected MyInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        setFactory2(new MyFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new MyInflater(this,newContext);
    }



    private class MyFactory implements Factory2 {
        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view"
        };
        public MyFactory(MyInflater myInflater) {

        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = null;
            view = createMyView(name,context,attrs);
            return null;
        }

        private View createMyView(String name, Context context, AttributeSet attrs) {
            if (name.contains(".")){
                return reflectView(name,null,context,attrs);
            }else {
                for (String prefix : sClassPrefix){
                    View view = reflectView(name,prefix,context,attrs);
                    if (view != null){
                        return view;
                    }
                }
            }
            return null;
        }

        private View reflectView(String name, String prefix, Context context, AttributeSet attrs) {
            //通过系统inflater创建视图，读取系统属性
            try {
                return inflater.createView(name,prefix,attrs);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }
    }
}
