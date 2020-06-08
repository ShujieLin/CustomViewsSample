package com.example.customviewsample.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.customviewsample.R;

public class LayoutParams extends ViewGroup.MarginLayoutParams {
    public static final int POSITION_MIDDLE = 0; // 中间
    public static final int POSITION_LEFT = 1; // 左上方
    public static final int POSITION_RIGHT = 2; // 右上方
    public static final int POSITION_BOTTOM = 3; // 左下角
    public static final int POSITION_RIGHTANDBOTTOM = 4; // 右下角

    public static int ROW;
    public static int COLUMS;

    public int mPostion = POSITION_LEFT;//默认在左上角

    public LayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
        TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.CusGridLayout);
        mPostion = typedArray.getInt(R.styleable.CusGridLayout_layout_position_grid,mPostion);
        typedArray.recycle();
    }


}
