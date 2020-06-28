package com.example.customviewsample.animation_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class CusView extends View {
    private int mWidth ;
    private int mHeight ;

    public void setWidth(int width) {
        this.mWidth = width;
    }


    public CusView(Context context) {
        super(context);
    }

    public CusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        mWidth = layoutParams.width;
        mHeight = layoutParams.height;


        int widthSpecMode = MeasureSpec. getMode( widthMeasureSpec);
        int widthSpecSize = MeasureSpec. getSize( widthMeasureSpec);
        int heightSpecMode = MeasureSpec. getMode( heightMeasureSpec);
        int heightSpecSize = MeasureSpec. getSize( heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension( mWidth, mHeight);
        } else if (widthSpecMode == MeasureSpec. AT_MOST) {
            setMeasuredDimension( mWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec. AT_MOST) {
            setMeasuredDimension( widthSpecSize, mHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,mWidth,mHeight,paint);
    }



}
