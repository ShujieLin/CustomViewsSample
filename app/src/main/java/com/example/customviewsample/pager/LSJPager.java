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
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"ACTION_UP()");

                //添加判断，应该滑动到哪一页

                int targetIndex = 0;
                //计算有效滑动距离，假如滑动超过一半，则进行滑动
                if ((event.getX() - mFirstTouchX) > (getWidth() / 2)){//左移动
                    //计算目标页index
//                    targetIndex = (mCurrentIndex - 1) < 0 ? 0 : mCurrentIndex - 1;
                    targetIndex = mCurrentIndex - 1;
                }else if ((event.getX() - mFirstTouchX) < (getWidth() / 2)){//右移动
                    targetIndex = mCurrentIndex + 1;
                }else {
                    targetIndex = mCurrentIndex;
                }

                Log.d(TAG,"ACTION_UP()" + " X = " + event.getX() + " mFirstTouchX = " + mFirstTouchX);


                moveToDest(targetIndex);
                break;
            default:
                break;
        }

        //判断左滑还是右滑


        return true;
    }

    private void moveToDest(int targetIndex) {
//        mCurrentIndex = (targetIndex > 0) ? targetIndex : 0;
//        mCurrentIndex = (targetIndex <= getChildCount() ? targetIndex : targetIndex - 1);
        //边界处理
        mCurrentIndex = targetIndex < 0 ? targetIndex + 1 : targetIndex;
        //index = 2 count = 3
        mCurrentIndex = (targetIndex >= getChildCount()) ? targetIndex - 1 : targetIndex;


        //获取滑动距离
        int distanceX = mCurrentIndex * getWidth() - getScrollX();
        Log.d(TAG,"moveToDest()" + "mCurrentIndex = " + mCurrentIndex + " distanceX = " + distanceX);

        mMyScroller.startScroll(getScrollX(),0,distanceX,0);
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
