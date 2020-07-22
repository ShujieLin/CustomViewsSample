package com.example.customviewsample.set_factory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customviewsample.R;


public class Main16Activity extends AppCompatActivity {
    private static final String TAG = "Main16Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.i(TAG, "onCreateView: name = " + name);
                int n = attrs.getAttributeCount();
                for (int i = 0;i < n;i ++){
                    Log.i(TAG, "onCreateView: " + attrs.getAttributeName(i) + "," + attrs.getAttributeValue(i));
                }

                //你可以在这里直接new自定义View

                //你可以在这里将系统类替换为自定义View

                //appcompat 创建view代码

                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);

                return view;
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main16);
    }


    /**
     * 统一设置app中所有字体
     * 很多开发者的实现是这样的，在BaseActivity的onCreate中去从跟布局去递归遍历所有的View
     * 这种方式虽然方便，但是肯定会带来一定性能问题。
     * @param root
     * @param typeface
     */
    public void setTypeface(ViewGroup root, Typeface typeface){
        if(root==null || typeface==null){
            return;
        }
        int count = root.getChildCount();
        for(int i=0;i<count;++i){
            View view = root.getChildAt(i);
            if(view instanceof TextView){
                ((TextView)view).setTypeface(typeface);
            }else if(view instanceof ViewGroup){
                setTypeface((ViewGroup)view, typeface);
            }
        }
    }
}
