package com.example.customviewsample.animation_sample.views.sample09;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Sample09ArgbEvaluatorView extends View {

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    int color = 0xffff0000;

    public Sample09ArgbEvaluatorView(Context context) {
        super(context);
    }

    public Sample09ArgbEvaluatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample09ArgbEvaluatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        mPaint.setColor(color);
        canvas.drawCircle(width / 2, height / 2, width / 6,mPaint);
    }
}
