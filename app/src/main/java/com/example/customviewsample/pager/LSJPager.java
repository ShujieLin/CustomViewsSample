package com.example.customviewsample.pager;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class LSJPager extends ViewGroup{

    private static final String TAG = LSJPager.class.getSimpleName();

    private int mFirstTouchX;

    private MyScroller mMyScroller;

    private GestureDetector mGestureDetector;

    private int mCurrentIndex;



    private Context mContext;

    public LSJPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mMyScroller = new MyScroller(mContext);
        mGestureDetector = new GestureDetector(mContext, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX,0);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0;i < getChildCount();i ++){
            View childView = getChildAt(i);
            childView.layout(0 + getWidth() * i,0,getWidth() * (i + 1),getHeight());
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"ACTION_DOWN()");
                mFirstTouchX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"ACTION_MOVE()");
                break;
            case MotionEvent.ACTION_UP : // 抬起
                int nextId = 0; // 记录下一个View的id
                if (event.getX() - mFirstTouchX > getWidth() / 2) {
                    // 手指离开点的X轴坐标-firstDownX > 屏幕宽度的一半，左移
                    nextId = (mCurrId - 1) <= 0 ? 0 : mCurrId - 1;
//                    nextId = mCurrId - 1;
                } else if (mFirstTouchX - event.getX() > getWidth() / 2) {
                    // 手指离开点的X轴坐标 - firstDownX < 屏幕宽度的一半，右移
                    nextId = mCurrId + 1;
                } else {
                    nextId = mCurrId;
                }

                moveToDest(nextId);
                break;
        }

        //判断左滑还是右滑
        return true;
    }


    private int mCurrId;
    private void moveToDest(int nextId) {
// nextId的合理范围是，nextId >=0 && nextId <= getChildCount()-1
        mCurrId = (nextId >= 0) ? nextId : 0;
        mCurrId = (nextId <= getChildCount() - 1)
                ? nextId
                : (getChildCount() - 1);

        // 视图移动,太直接了，没有动态过程
        // scrollTo(mCurrId * getWidth(), 0);
        // 要移动的距离 = 最终的位置 - 现在的位置
        int distanceX = mCurrId * getWidth() - getScrollX();
        // 设置运行的时间
        mMyScroller.startScroll(getScrollX(), 0, distanceX, 0);
        // 刷新视图

        invalidate();
    }


    @Override
    public void computeScroll() {
        if (mMyScroller.computeOffset()){
            int currX = (int) mMyScroller.getCurrX();
            scrollTo(currX,0);
            invalidate();
        }
    }
}
