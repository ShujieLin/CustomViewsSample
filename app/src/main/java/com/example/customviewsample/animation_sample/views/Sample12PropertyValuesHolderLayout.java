package com.example.customviewsample.animation_sample.views;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.customviewsample.R;

public class Sample12PropertyValuesHolderLayout extends RelativeLayout {

    View view;
    Button animateBt;

    public Sample12PropertyValuesHolderLayout(Context context) {
        super(context);
    }

    public Sample12PropertyValuesHolderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample12PropertyValuesHolderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
                PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX",0,1);
                PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY",0,1);
                PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha",0,1);

                ObjectAnimator.ofPropertyValuesHolder(view,holder1,holder2,holder3).start();

            }
        });
    }
}
