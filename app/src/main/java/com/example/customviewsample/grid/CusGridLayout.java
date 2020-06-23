package com.example.customviewsample.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;


/**
 * 网格容器
 */
public class CusGridLayout extends ViewGroup {

    private static final String TAG = CusGridLayout.class.getSimpleName();
    /**
     * 页数
     */
    private int mPagerCount;

    /**
     * 子view控件数量
     */
    private int mChildViewCount;

    /**
     * 行数
     */
    private int mRowCount;

    /**
     * 列数
     */
    private int mColumnCount;

    public CusGridLayout(Context context) {
        super(context);
    }

    public CusGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mRowCount = 4;
        mColumnCount = 4;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        init();
        mChildViewCount = getChildCount();
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec));

        for (int i = 0;i < getChildCount();i ++){
            View childView = getChildAt(i);

            int selfWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int selfWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            LayoutParams lp = childView.getLayoutParams();
            switch (lp.width){
                case LayoutParams.MATCH_PARENT:
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST){
                        
                    }
                    break;

            }
        }

    }




    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childWidth = getWidth() / mColumnCount;
        int childHeight = getHeight() / mRowCount;
        //模仿gridview的效果，例如容器为：3行 x 4列，自动换行
        for (int i = 0;i < mChildViewCount;i ++){
            View childView = getChildAt(i);

            //获取每个子view的x，y坐标
            int x = i / mColumnCount;
            int y = i % mColumnCount;

            int childTop = childHeight * x;
            int childLeft = childWidth * y;
            int childRight = childLeft + childWidth;
            int childBottom = childTop + childHeight;
            Log.d(TAG,"onLayout()" + "left = " + childLeft +  " top = " + childTop  + " right = " + childRight + " bottom = " + childBottom);

            childView.layout(childLeft,childTop,childRight,childBottom);
        }


    }




    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new com.example.customviewsample.grid.LayoutParams(getContext(),attrs);
    }
}
