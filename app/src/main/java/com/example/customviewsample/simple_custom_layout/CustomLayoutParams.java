package com.example.customviewsample.simple_custom_layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.customviewsample.R;

public class CustomLayoutParams extends ViewGroup.MarginLayoutParams {
    public static final int POSITION_MIDDLE = 0; // 中间
    public static final int POSITION_LEFT = 1; // 左上方
    public static final int POSITION_RIGHT = 2; // 右上方
    public static final int POSITION_BOTTOM = 3; // 左下角
    public static final int POSITION_RIGHTANDBOTTOM = 4; // 右下角

    public int postion = POSITION_LEFT;//默认在左上角

    public CustomLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
        TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.CustomLinearLayout);
        postion = typedArray.getInt(R.styleable.CustomLinearLayout_layout_position,postion);
        typedArray.recycle();
    }

}
