package com.shujiel.eventdistribution;

import com.shujiel.eventdistribution.listener.OnClickListener;
import com.shujiel.eventdistribution.listener.OnTouchListener;

public class View {
    private int left;
    private int top;
    private int right;
    private int bottom;

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnClickListener;
    public void setOnClickListener(OnClickListener onClickListener){
        this.mOnClickListener = onClickListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener){
        this.mOnTouchListener = onTouchListener;
    }

    public View(int left,int top,int right,int bottom){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    /**
     * 是否在view内部
     * @param x
     * @param y
     * @return
     */
    public boolean isContainer(int x,int y){
        if (x >= left && x < right && y>= top && y < bottom){
            return true;
        }
        return false;
    }

    /**
     * 事件分发
     * @param event
     * @return
     */
    public boolean dispatchTouchEvent(MotionEvent event){
        System.out.println("view dispatchTouchEvent");
        boolean result = false;
        //假如设置了onTouch事件并返回true，则消耗事件
        if (mOnTouchListener != null && mOnTouchListener.onTouch(this,event)){
            result = true;
        }
        //假如mOnTouchListener.onTouch没有消耗事件，但是View自身通过onTouchEvent消耗了事件，则事件被消耗
        if (!result && onTouchEvent(event)){
            result = true;
        }

        return result;
    }

    /**
     *
     * @param event
     * @return
     */
    private boolean onTouchEvent(MotionEvent event) {
        if (mOnClickListener != null){
            mOnClickListener.onClick(this);
            return true;
        }
        return false;
    }

}
