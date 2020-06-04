package com.example.customviewsample.SlideTab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 *
 *
 */
public class SlideTab3 extends View {


    private static final String TAG = SlideTab3.class.getSimpleName();

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

    /**
     * 线的长度，自适应
     */
    private int mLineLength ;


    public SlideTab3(Context context) {
        this(context,null);
    }

    public SlideTab3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideTab3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG,"init()" + "getWidth = " + getWidth() + " getHeight = " + getHeight());

        mColorSelected = Color.RED;
        mColorUnSelected = Color.GRAY;
        mCirclesCount = 12;
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

        //根据用户设置的数据来设置对应的尺寸
        mLineLength = (getWidth() - (mPaddinLeft + mPaddingRight) - (mCirclesCount * mDiameter)) / (mCirclesCount - 1);


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height;
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = mDiameter + mPaddingTop + mPaddingBottom;
        }

        setMeasuredDimension(widthSize,height);

        Log.d(TAG,"onMeasure()" + "getWidth = " + getWidth() + " getHeight = " + getHeight() + " mLineLength = " + mLineLength);

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
            int cx = (mPaddinLeft + (mDiameter / 2))+ ((mDiameter + mLineLength)* i);
            int cy = (mDiameter / 2) + mPaddingTop;
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
                canvas.drawLine(cx + (mDiameter / 2) , cy,cx + (mDiameter / 2) +  mLineLength ,cy,paintLine);
            }

        }
    }


    /**
     * 是否拖动中
     */
    private boolean mIsSliding = false;
    /**
     * 手指坐标
     */
    private float mSlidX,mSlidY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mSlidX = event.getX();
        mSlidY = event.getY();


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mIsSliding = true;
                Log.d(TAG,"ACTION_DOWN" + " x = " + mSlidX + " y = " + mSlidY);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"ACTION_MOVE" + " x = " + mSlidX + " y = " + mSlidY);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"ACTION_UP" + " x = " + mSlidX + " y = " + mSlidY);
                break;

        }

        return true;
    }

}
