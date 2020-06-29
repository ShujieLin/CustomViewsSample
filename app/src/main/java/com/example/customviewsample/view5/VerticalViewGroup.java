package com.example.customviewsample.view5;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class VerticalViewGroup extends ViewGroup {
    private int mViewsWidth;
    private int mViewsHeight;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mViewGruopWidth;
    private int mViewGroupHeight;
    private int mMarginLeft;
    private int mMarginTop;
    private int mMarginRight;
    private int mMarginBottom;


    public VerticalViewGroup(Context context) {
        super(context);
    }

    public VerticalViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewsWidth = 0;
        mViewsHeight = 0;
        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
        mPaddingRight = getPaddingRight();
        mPaddingBottom = getPaddingBottom();
        int childCount = getChildCount();
        for (int i = 0;i < childCount;i ++){
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            /**历遍所有子元素，容器宽度 = 所有子元素中的宽度的最大值*/
            mViewsWidth = Math.max(mViewsWidth,childView.getMeasuredWidth());
            /**历遍所有子元素，容器高度 = 叠加所有子元素的高度*/
            mViewsHeight += childView.getMeasuredHeight();

            /**下面添加margin*/
            mMarginLeft = Math.max(0,lp.leftMargin);
            mMarginTop += lp.topMargin;
            mMarginRight = Math.max(0,lp.rightMargin);
            mMarginBottom += lp.bottomMargin;
        }

        /**用于处理ViewGroup的wrap_content的情况,容器宽度 = 容器宽度 + 容器左右内边距*/
        mViewGruopWidth = mPaddingLeft + mViewsWidth + mPaddingRight + mMarginLeft + mMarginRight;
        /**用于处理ViewGroup的wrap_content的情况,容器高度 = 容器高度 + 容器左右内边距*/
        mViewGroupHeight = mPaddingTop + mViewsHeight + mPaddingBottom + mMarginTop + mMarginBottom;
        /**测量容器宽高并保存*/
        setMeasuredDimension(
                measureWidth(widthMeasureSpec,mViewGruopWidth),
                measureHeight(heightMeasureSpec,mViewGroupHeight)
        );

    }

    private int measureHeight(int heightMeasureSpec, int viewGroupHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode){
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                /**取剩余高度和所有子View + padding的值进行比较，取小的作为ViewGroup的高度*/
                result = Math.min(viewGroupHeight,specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     *
     * @param widthMeasureSpec
     * @param viewGruopWidth
     * @return
     */
    private int measureWidth(int widthMeasureSpec, int viewGruopWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode){
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                /**取剩余宽度和所有子View + padding的值进行比较，取小的作为ViewGroup的宽度,
                 * 防止出现子元素的宽度比容器还大的情况，从而导致子元素超出了父容器的边界*/
                result = Math.min(viewGruopWidth,specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            final int childCount = getChildCount();
            int top = mPaddingTop;
            // TODO: 2020/6/24 下面还差rightMargin没添加
            for (int i = 0;i < childCount;i ++){
                View childView = getChildAt(i);
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                int left = mPaddingLeft + lp.leftMargin;
                top += lp.topMargin;
                childView.layout(left,top,left + childView.getMeasuredWidth(),top + childView.getMeasuredHeight());
                top += (childView.getMeasuredHeight() + lp.bottomMargin);
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(),attrs);
    }

}
