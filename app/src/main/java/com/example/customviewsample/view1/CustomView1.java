package com.example.customviewsample.view1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class CustomView1 extends View {
    private int mWidth = 0;
    private int mHeight = 0;

    private int mMeasureWidth = 0;
    private int mMeasureHeight = 0;

    public CustomView1(Context context) {
        super(context);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

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

    /**
     * 注意：某些极端情况下，系统需要多次measure才能确定最终的测量宽高，一个好习惯是在onLayout方法获取View的测量宽高
     * getMeasuredWidth是获取的view的原始大小
     * getWidth是或者view的最终显示大小
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mMeasureWidth = getMeasuredWidth();
        mMeasureHeight = getMeasuredHeight();
        mWidth = getWidth();
        mHeight = getHeight();
    }

    int mRectFLeft;
    int mRectFTop;
    int mRectFRight;
    int mRectFBottom;

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);

        //添加padding
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        mRectFLeft = 0 + paddingLeft;
        mRectFTop = 0 + paddingTop;
        mRectFRight = mMeasureWidth - paddingRight;
        mRectFBottom = mMeasureHeight - paddingBottom;
        RectF rectF = new RectF(mRectFLeft,mRectFTop,mRectFRight,mRectFBottom);

        canvas.save();
        canvas.drawRect(rectF,paint);
        canvas.restore();

        drawSize(canvas);
    }

    private void drawSize(Canvas canvas) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        String text1 = "尺寸: "
                + "mMeasureWidth = " + mMeasureWidth
                + "  mMeasureHeight = " + mMeasureHeight;
        String text2 = "尺寸: "
                + "mWidth = " + mWidth
                + "  mHeight = " + mHeight;
        StaticLayout staticLayout1 = new StaticLayout(text1, textPaint,400, Layout.Alignment.ALIGN_NORMAL,1,0,true);
        StaticLayout staticLayout2 = new StaticLayout(text2, textPaint,400, Layout.Alignment.ALIGN_NORMAL,1,0,true);

        canvas.save();
        canvas.translate(mRectFLeft,mRectFTop);
        staticLayout1.draw(canvas);
        canvas.translate(0,staticLayout1.getHeight() + 10);
        staticLayout2.draw(canvas);
        canvas.restore();
    }



}
