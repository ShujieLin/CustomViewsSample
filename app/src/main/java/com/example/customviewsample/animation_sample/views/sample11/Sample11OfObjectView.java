package com.example.customviewsample.animation_sample.views.sample11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customviewsample.animation_sample.Utils;

public class Sample11OfObjectView extends View {

    public static final float RADIUS = Utils.dpToPixel(20);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
        invalidate();
    }

    PointF position = new PointF();


    public Sample11OfObjectView(Context context) {
        super(context);
    }

    public Sample11OfObjectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample11OfObjectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float innerPaddingLeft = RADIUS * 1;
        float innterPaddingRight = RADIUS * 1;
        float innterPaddingTop = RADIUS * 1;
        float innterPaddingBottom = RADIUS * 3;
        float width = getWidth() - innerPaddingLeft - innterPaddingRight - RADIUS * 2;
        float height = getHeight() - innterPaddingTop - innterPaddingBottom - RADIUS * 2;

        canvas.drawCircle(innerPaddingLeft + RADIUS + width * position.x, innterPaddingTop + RADIUS + height * position.y, RADIUS, paint);
    }


}
