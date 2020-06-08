package com.example.customviewsample.simple_custom_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomLinearLayout extends ViewGroup {

    private int mChildViewCount;

    public CustomLinearLayout(Context context) {
        super(context);
    }


    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mChildViewCount = getChildCount();
        
        int layoutWidth = 0;
        int layoutHeight = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){
            layoutWidth = sizeWidth;
        }else {
            for (int i = 0;i < mChildViewCount;i ++){
                View childView = getChildAt(i);
                CustomLayoutParams layoutParams = (CustomLayoutParams) childView.getLayoutParams();
                int childWidth = childView.getMeasuredWidth();
                int childSeizeWidth = childWidth + layoutParams.leftMargin + layoutParams.rightMargin;
                layoutWidth = childSeizeWidth > layoutWidth ? childSeizeWidth : layoutWidth;
            }
        }

        if (heightMode == MeasureSpec.EXACTLY){
            layoutHeight = sizeHeight;
        }else {
            for (int i = 0;i < mChildViewCount;i ++){
                View childView = getChildAt(i);
                CustomLayoutParams layoutParams = (CustomLayoutParams) childView.getLayoutParams();
                int childHeight = childView.getMeasuredHeight();
                int childSeizeHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                layoutHeight = childSeizeHeight > layoutHeight ? childSeizeHeight : layoutHeight;
            }
        }

        setMeasuredDimension(layoutWidth,layoutHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;

        CustomLayoutParams params = null;

        for (int i = 0;i < mChildViewCount;i ++){
            View childView = getChildAt(i);
            childMeasureWidth = childView.getMeasuredWidth();
            childMeasureHeight = childView.getMeasuredHeight();
            params = (CustomLayoutParams) childView.getLayoutParams();

            switch (params.postion){
                case CustomLayoutParams.POSITION_MIDDLE:
                    l = (getWidth() - childMeasureWidth) / 2 - params.rightMargin + params.leftMargin;
                    t = (getHeight() - childMeasureHeight) / 2 + params.topMargin - params.bottomMargin;
                    break;
                case CustomLayoutParams.POSITION_LEFT:
                    l = 0 + params.leftMargin;
                    t = 0 + params.topMargin;
                    break;
                case CustomLayoutParams.POSITION_RIGHT:
                    l = getWidth() - childMeasureWidth  - params.rightMargin;
                    t = 0 + params.topMargin;
                    break;
                case CustomLayoutParams.POSITION_BOTTOM:
                     l = 0 + params.leftMargin;
                     t = getHeight() - childMeasureHeight - params.bottomMargin;
                     break;
                case CustomLayoutParams.POSITION_RIGHTANDBOTTOM:
                    l = getWidth() - childMeasureWidth - params.rightMargin;
                    t = getHeight() - childMeasureHeight - params.bottomMargin;
                    break;
                default:
                    break;
            }

            childView.layout(l,t,l + childMeasureWidth,t + childMeasureHeight);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(),attrs);
    }

}
