package com.example.customviewsample.animation_sample.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.customviewsample.R;

public class Sample13AnimatorSetLayout extends RelativeLayout {
    View view;
    Button animateBt;
    public Sample13AnimatorSetLayout(Context context) {
        super(context);
    }

    public Sample13AnimatorSetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample13AnimatorSetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
                view.setTranslationX(-200f);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view,"alpha",0,1);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "translationX", -200, 200);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "rotation", 0, 1080);
                animator3.setDuration(1000);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(animator1).before(animator2);
                animatorSet.playTogether(animator2,animator3);

                animatorSet.start();

            }
        });

    }
}
