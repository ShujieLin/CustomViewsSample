package com.example.customviewsample.animation_sample.views.sample08;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customviewsample.animation_sample.Utils;

public class Sample08ObjectAnimatorView extends View {
    final float mRadius = Utils.dpToPixel(80);

    RectF mArcRectF = new RectF();
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    float progress;



    {
        mPaint.setTextSize(Utils.dpToPixel(40));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public Sample08ObjectAnimatorView(Context context) {
        super(context);
    }

    public Sample08ObjectAnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample08ObjectAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        //画圆环
        mPaint.setColor(Color.parseColor("#E91E63"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(Utils.dpToPixel(15));
        mArcRectF.set(centerX - mRadius,centerY - mRadius,centerX + mRadius,centerY + mRadius);
        canvas.drawArc(mArcRectF,135,progress * 2.7f,false,mPaint);

        //画文字
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText((int)progress + "%",centerX,centerY - (mPaint.ascent() + mPaint.descent()) / 2,mPaint);

    }

}
