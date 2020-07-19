package com.example.customviewsample.animation_sample.views.sample09;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.customviewsample.R;

public class Sample09ArgbEvaluatorLayout extends RelativeLayout {

    Sample09ArgbEvaluatorView mView;
    Button mAnimateBt;

    public Sample09ArgbEvaluatorLayout(Context context) {
        super(context);
    }

    public Sample09ArgbEvaluatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample09ArgbEvaluatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mView = findViewById(R.id.objectAnimatorView);
        mAnimateBt = findViewById(R.id.animateBt);

        mAnimateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mView,"color",0xffff0000, 0xff00ff00);
                animator.setEvaluator(new ArgbEvaluator());
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });
    }
}
