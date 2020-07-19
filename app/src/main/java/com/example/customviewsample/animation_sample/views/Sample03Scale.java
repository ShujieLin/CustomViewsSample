package com.example.customviewsample.animation_sample.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.customviewsample.R;

public class Sample03Scale extends RelativeLayout {
    Button mButton;
    ImageView mImageView;
    private int state = 0;
    public Sample03Scale(Context context) {
        super(context);
    }

    public Sample03Scale(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample03Scale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mButton = findViewById(R.id.animateBt);
        mImageView = findViewById(R.id.imageView);

        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state){
                    case 0:
                        mImageView.animate().scaleX(1.5f);
                        break;
                    case 1:
                        mImageView.animate().scaleX(1);
                        break;
                    case 2:
                        mImageView.animate().scaleY(0.5f);
                        break;
                    case 3:
                        mImageView.animate().scaleY(1);
                        break;

                }
                state ++;
                if (state == 4){
                    state = 0;
                }
            }
        });

    }
}
