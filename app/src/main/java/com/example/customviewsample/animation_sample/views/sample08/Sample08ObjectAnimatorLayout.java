package com.example.customviewsample.animation_sample.views.sample08;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.customviewsample.R;

public class Sample08ObjectAnimatorLayout extends RelativeLayout {
    Sample08ObjectAnimatorView view;
    Button animateBt;
    public Sample08ObjectAnimatorLayout(Context context) {
        super(context);
    }

    public Sample08ObjectAnimatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample08ObjectAnimatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
                // TODO 在这里处理点击事件，用 ObjectAnimator 播放动画
                // 1. 用 ObjectAnimator 创建 Animator 对象
                // 2. 用 start() 执行动画
                // *. 记得在 Practice08ObjectAnimatorView 中为 progress 添加 setter/ getter 方法！
                ObjectAnimator animator = ObjectAnimator.ofFloat(view,"progress",0,100);
                animator.setDuration(1000);
                animator.start();
            }
        });




    }
}
