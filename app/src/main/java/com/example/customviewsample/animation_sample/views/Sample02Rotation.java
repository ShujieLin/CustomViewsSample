package com.example.customviewsample.animation_sample.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.customviewsample.R;

public class Sample02Rotation extends RelativeLayout {
    Button mButton;
    ImageView mImageView;

    int state = 0;

    public Sample02Rotation(Context context) {
        super(context);
    }

    public Sample02Rotation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample02Rotation(Context context, AttributeSet attrs, int defStyleAttr) {
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
             switch (state) {
                 case 0:
                     mImageView.animate().rotation(180);
                     break;
                 case 1:
                     mImageView.animate().rotation(0);
                     break;
                 case 2:
                     mImageView.animate().rotationX(180);
                     break;
                 case 3:
                     mImageView.animate().rotationX(0);
                     break;
                 case 4:
                     mImageView.animate().rotationY(180);
                     break;
                 case 5:
                     mImageView.animate().rotationY(0);
                     break;
             }
             state++;
             if (state == 6) {
                 state = 0;
             }
         }
     });

    }
}
