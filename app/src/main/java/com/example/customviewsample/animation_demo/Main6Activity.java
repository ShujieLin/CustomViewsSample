package com.example.customviewsample.animation_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import com.example.customviewsample.R;

/**
 * 需求：给Button加一个动画，让这个Button的宽度增加到500px
 */
public class Main6Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = Main6Activity.class.getSimpleName();
    Button mButton;
    Button mButton2;
    CusView mCusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        mButton = findViewById(R.id.btn);
        mButton.setOnClickListener(this);

        mButton2 = findViewById(R.id.btn2);
        mButton2.setOnClickListener(this);


        mCusView = findViewById(R.id.cusview);
        mCusView.setOnClickListener(this);

    }

    private void performAnimate() {
        ObjectAnimator.ofInt(mButton2,"width",500).setDuration(5000).start();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn){
            /**View动画看起来好像宽度增加了，实际上不是，只是Button被放大了，这时候Button背景以及上面的文本都被拉伸了*/
            ScaleAnimation animation = new ScaleAnimation(1, 2, 1, 1, Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f);
            animation.setDuration(2000);
            animation.setFillAfter(true);
            mButton.startAnimation(animation);
            /**用View动画实现效果显然很差*/
        }else if (v.getId() == R.id.btn2){
            performAnimate();
        }

        if (v.getId() == R.id.cusview){
            performAnimate_cusvie();
        }

    }

    private void performAnimate_cusvie() {
        ViewWrapper wrapper = new ViewWrapper(mCusView);
        ObjectAnimator.ofInt(wrapper,"width",500).setDuration(2000).start();
    }


    private static class ViewWrapper{
        private View mTarget;
        public ViewWrapper(View target){
            mTarget = target;
        }

        public void setWidth(int width){
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

    }


}
