package com.example.customviewsample.turn_gray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.customviewsample.R;

public class Main15Activity extends AppCompatActivity {
    private static final String TAG = "Main15Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        try {
            if ("FrameLayout".equals(name)){
                int count = attrs.getAttributeCount();
                for (int i = 0;i < count;i ++){
                    String attributeName = attrs.getAttributeName(i);
                    String attributeValue = attrs.getAttributeValue(i);
                    Log.i(TAG, "onCreateView: " + "attributeName = " + attributeName + " attributeValue = " + attributeValue);
                    if (attributeName.equals("id")){
                        int id = Integer.parseInt(attributeValue.substring(1));
                        //获取属性资源id
                        String idVal = getResources().getResourceName(id);
                        if ("android:id/content".equals(idVal)){
                            GrayFrameLayout grayFrameLayout = new GrayFrameLayout(context,attrs);
                            return grayFrameLayout;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return super.onCreateView(name, context, attrs);
    }
}