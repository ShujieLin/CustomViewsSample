package com.example.customviewsample.animation_sample.views.sample11;

import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.customviewsample.R;

public class Sample11OfObjectLayout extends RelativeLayout {
    Sample11OfObjectView view;
    Button animateBt;

    public Sample11OfObjectLayout(Context context) {
        super(context);
    }

    public Sample11OfObjectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample11OfObjectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view =  findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofObject(view, "position",
                        new PointFEvaluator(), new PointF(0, 0), new PointF(1, 1));
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(1000);
                animator.start();
            }
        });
    }
}
