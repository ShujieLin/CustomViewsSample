package com.example.customviewsample.view5;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.customviewsample.R;

public class CustomViewGroup extends ViewGroup {
    private int mChildrenSize;
    private int mChildWidth;

    public CustomViewGroup(Context context) {
        super(context);
        init();
    }


    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // TODO: 2020/6/23 这里不规范 ，默认不为0，应该根据LayoutParmas中宽高做处理

        int measureWidth = 0;
        int measureHeight = 0;

        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        // TODO: 2020/6/23 这里不规范，测量宽高的时候，没有考虑到它的padding以及子元素的margin
        if (childCount == 0){
            setMeasuredDimension(0,0);
        }else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);

            MyLayoutParams lp = (MyLayoutParams) childView.getLayoutParams();

            measureWidth = childView.getMeasuredWidth() * childCount + lp.leftMargin + lp.rightMargin;
            measureHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            setMeasuredDimension(measureWidth,measureHeight);
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize,measureHeight);
        }else if (widthSpecMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measureWidth,heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO: 2020/6/23 这里不完善之处在于没有考虑到自身padding以及子元素的marggin
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        for (int i = 0;i < childCount;i ++){
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE){
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft,0,childLeft + childWidth,childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        return new MyLayoutParams(getContext(),attrs);
    }

    class MyLayoutParams extends ViewGroup.MarginLayoutParams{

        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

    }

}
