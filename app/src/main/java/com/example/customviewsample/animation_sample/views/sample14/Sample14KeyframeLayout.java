package com.example.customviewsample.animation_sample.views.sample14;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.example.customviewsample.R;

public class Sample14KeyframeLayout extends RelativeLayout {

    Sample14KeyframeView view;
    Button animateBt;

    public Sample14KeyframeLayout(Context context) {
        super(context);
    }

    public Sample14KeyframeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample14KeyframeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = findViewById(R.id.objectAnimatorView);
        animateBt = findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyframe keyframe1 = Keyframe.ofFloat(0,0);
                Keyframe keyframe2 = Keyframe.ofFloat(0.5f,100);
                Keyframe keyframe3 = Keyframe.ofFloat(1,80);
                PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress",keyframe1,keyframe2,keyframe3);

                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view,holder);
                animator.setDuration(2000);
                animator.setInterpolator(new FastOutSlowInInterpolator());
                animator.start();
            }
        });

    }
}
