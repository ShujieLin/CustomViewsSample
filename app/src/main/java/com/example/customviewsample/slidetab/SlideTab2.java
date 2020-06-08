package com.example.customviewsample.slidetab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class SlideTab2 extends View {

    private static final String TAG = SlideTab2.class.getSimpleName();

    /**
     * 圆点直径
     */
    private int  mDiameter;

    /**
     * 圆与圆之间的距离，以圆心为测量点
     */
    private int mCirclesDstance;

    /**
     * 选中颜色
     */
    private int mColorSelected;

    /**
     * 未选中颜色
     */
    private int mColorUnSelected;

    /**
     * 圆的数量
     */
    private int mCirclesCount;

    /**
     * 选中下标
     */
    private int mSelectedIndex;

    private int mPaddinLeft;

    private int mPaddingRight;

    private int mPaddingTop;

    private int mPaddingBottom;




    public SlideTab2(Context context) {
        this(context,null);
    }

    public SlideTab2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideTab2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG,"init()" + "getWidth = " + getWidth() + " getHeight = " + getHeight());

        mColorSelected = Color.RED;
        mColorUnSelected = Color.GRAY;
        mCirclesCount = 4;
        mSelectedIndex = 1;

        mPaddingTop = 50;
        mPaddinLeft = 50;
        mPaddingBottom = 50;
        mPaddingRight = 50;

        mDiameter = 100;
        mCirclesDstance = 200;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure()" + "getWidth = " + getWidth() + " getHeight = " + getHeight());

        //根据用户设置的数据来设置对应的尺寸



    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG,"onLayout()" + "getWidth = " + getWidth() + " getHeight = " + getHeight());

    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw()" + "getWidth = " + getWidth() + " getHeight = " + getHeight());

        //画n个圆，n - 1条线
        for (int i = 0;i < mCirclesCount;i ++){

            //每个圆的圆心坐标
            int cx = (mDiameter / 2) + (mCirclesDstance * i);
            int cy = mDiameter / 2;
            Paint paintLine = new Paint();
            paintLine.setStrokeWidth(10);
            paintLine.setColor(mColorUnSelected);
            Paint paintCircle = new Paint();

            //画圆
            if (i != mSelectedIndex){
                paintCircle.setColor(mColorUnSelected);
            }else {
                paintCircle.setColor(mColorSelected);
            }
            canvas.drawCircle(cx,cy,mDiameter / 2,paintCircle);

            //画线
            if (i  < mCirclesCount - 1){
                canvas.drawLine(cx + (mDiameter / 2),cy,cx + mCirclesDstance,cy,paintLine);
            }

        }







    }

}
