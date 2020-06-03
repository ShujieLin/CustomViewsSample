package com.example.customviewsample.dragview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomLinearLayout extends ViewGroup {

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 所有子控件测量自己的大小，
     * 并且根据子控件的大小完成自己的尺寸测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //计算所有childview的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //测量保存layout的宽高
        //注意：使用getDefaultSize时，wrap_content和match_parent都是填充屏幕
        setMeasuredDimension(
                getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec)
        );

    }


    /**
     * 为所有子控件摆放位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;//容器占据的宽度
        int layoutHeight = 0;//容器占据的高度
        int maxChildHeight = 0; //一行子控件中高度最高的子控件的高度

        for (int i = 0;i < count;i ++){
            View child = getChildAt(i);
            //获取子控件的高度和宽度
            //注意：这里不能使用getWidth和getHeight，这两个方法必须在onLayout执行完才能正确获取
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            if (layoutWidth < getWidth()){
                //假如一行没排满，继续往右边排列
                l = layoutWidth;
                r = l + childMeasureWidth;
                t = layoutHeight;
                b = t + childMeasureHeight;
            }else {
                //排满一行后，换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                l = layoutWidth;
                r = l + childMeasureWidth;
                t = layoutHeight;
                b = t + childMeasureHeight;
            }

            layoutWidth += childMeasureWidth;//宽度累加
            if (childMeasureHeight > maxChildHeight){
                maxChildHeight = childMeasureHeight;
            }

            //确定子控件位置
            child.layout(l,t,r,b);
        }

    }

}
