package com.example.customviewsample.image_loader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //这里巧妙地将heightMeasureSpec替换为widthMeasureSpec，这样就可以获取一个等宽高的ImageView了
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
