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
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        //计算所有childview的宽和高
//        measureChildren(widthMeasureSpec,heightMeasureSpec);
//
//        //测量保存layout的宽高
//        //注意：使用getDefaultSize时，wrap_content和match_parent都是填充屏幕
//        setMeasuredDimension(
//                getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),
//                getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec)
//        );
//
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int layoutWidth = 0;
        int layoutHeight = 0;


        int cWidth = 0;
        int cHeight = 0;
        int count = getChildCount();

//        //计算所有childView的宽高
//        measureChildren(widthMeasureSpec,heightMeasureSpec);



        //计算所有的childView的宽和高
        for (int i = 0;i < count;i ++){
            View child = getChildAt(i);
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);
        }
        CustomLayoutParames parames = null;


        if (widthMode == MeasureSpec.EXACTLY){//假如容器为精确模式，直接使用父窗体建议宽度
            layoutWidth = sizeWidth;
        }else {  //假如未指定或者wrap_content,按照包裹内容执行，宽度方向只需要拿到所有子控件中宽度最大的作为布局宽度
            for (int i = 0;i < count ;i ++){
                View child = getChildAt(i);
                cWidth = child.getMeasuredWidth();

                parames = (CustomLayoutParames) child.getLayoutParams();
                //获取控件的最大宽度
//                layoutWidth = cWidth > layoutWidth ? cWidth : layoutWidth;

                //获取子控件宽度和左右边距之和，作为这个子控件需要占据的宽度
                int marginWidth = cWidth + parames.leftMargin + parames.rightMargin;
                layoutWidth = marginWidth > layoutWidth ? marginWidth : layoutWidth;

            }
        }

        if (heightMode == MeasureSpec.EXACTLY){
            layoutHeight = sizeHeight;
        }else {
            for (int i = 0;i < count;i ++){
                View child = getChildAt(i);
                cHeight = child.getMeasuredHeight();
                parames = (CustomLayoutParames) child.getLayoutParams();
                int marginHeight = cHeight + parames.topMargin + parames.bottomMargin;
                layoutHeight = cHeight > layoutHeight ? cHeight : layoutHeight;
            }
        }

        setMeasuredDimension(layoutWidth,layoutHeight);
    }

//    /**
//     * 为所有子控件摆放位置
//     * @param changed
//     * @param l
//     * @param t
//     * @param r
//     * @param b
//     */
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        final int count = getChildCount();
//        int childMeasureWidth = 0;
//        int childMeasureHeight = 0;
//        int layoutWidth = 0;//容器占据的宽度
//        int layoutHeight = 0;//容器占据的高度
//        int maxChildHeight = 0; //一行子控件中高度最高的子控件的高度
//
//        for (int i = 0;i < count;i ++){
//            View child = getChildAt(i);
//            //获取子控件的高度和宽度
//            //注意：这里不能使用getWidth和getHeight，这两个方法必须在onLayout执行完才能正确获取
//            childMeasureWidth = child.getMeasuredWidth();
//            childMeasureHeight = child.getMeasuredHeight();
//            if (layoutWidth < getWidth()){
//                //假如一行没排满，继续往右边排列
//                l = layoutWidth;
//                r = l + childMeasureWidth;
//                t = layoutHeight;
//                b = t + childMeasureHeight;
//            }else {
//                //排满一行后，换行
//                layoutWidth = 0;
//                layoutHeight += maxChildHeight;
//                maxChildHeight = 0;
//
//                l = layoutWidth;
//                r = l + childMeasureWidth;
//                t = layoutHeight;
//                b = t + childMeasureHeight;
//            }
//
//            layoutWidth += childMeasureWidth;//宽度累加
//            if (childMeasureHeight > maxChildHeight){
//                maxChildHeight = childMeasureHeight;
//            }
//
//            //确定子控件位置
//            child.layout(l,t,r,b);
//        }
//
//    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        CustomLayoutParames parames = null;

        for (int i = 0;i < count;i ++){
            View child = getChildAt(i);
            //
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();

            parames = (CustomLayoutParames) child.getLayoutParams();
            switch (parames.postion){
                case CustomLayoutParames.POSITION_MIDDLE:
                    l = (getWidth() - childMeasureWidth) / 2 - parames.rightMargin + parames.leftMargin;
                    t = (getHeight() - childMeasureHeight) / 2 + parames.topMargin - parames.bottomMargin;
                    break;
                case CustomLayoutParames.POSITION_LEFT:
                    l = 0 + parames.leftMargin + parames.leftMargin;
                    t = 0 + parames.topMargin + parames.topMargin;
                    break;
                case CustomLayoutParames.POSITION_RIGHT:
                    l = getWidth() - childMeasureWidth  - parames.rightMargin;
                    t = 0 + parames.topMargin;
                    break;
                case CustomLayoutParames.POSITION_BOTTOM:
                     l = 0 + parames.leftMargin;
                     t = getHeight() - childMeasureHeight - parames.bottomMargin;
                     break;
                case CustomLayoutParames.POSITION_RIGHTANDBOTTOM:
                    l = getWidth() - childMeasureWidth - parames.rightMargin;
                    t = getHeight() - childMeasureHeight - parames.bottomMargin;
                    break;
                default:
                    break;
            }

            child.layout(l,t,l + childMeasureWidth,t + childMeasureHeight);

        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParames(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParames((MarginLayoutParams) p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParames(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParames;
    }

}
