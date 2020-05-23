package com.example.customviewsample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.Nullable;

public class CustomView extends View {
    private Context mContext;


    private static final String TAG = CustomView.class.getSimpleName();



    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        Log.d(TAG,"init()");
        mContext = context;
        getCoordinatePosition();
        getTouchSlop2();
        getVelocity();
        getGestureDetector();

    }

    /**
     * 手势检测
     */
    private void getGestureDetector() {


        GestureDetector gestureDetector = new GestureDetector(mContext,new GestureDetector.OnGestureListener() {
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


        GestureDetector.OnGestureListener listener = new GestureDetector.OnGestureListener() {
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
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        };

        GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener();



    }


    /**
     * 获取滑动速度
     */
    private void getVelocity() {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.computeCurrentVelocity(1000);//代表1000毫秒划过的像素数
        float xVelocity = velocityTracker.getXVelocity();//水平平均速度
        float yVelocity = velocityTracker.getYVelocity();//纵向平均速度

        //不使用的时候，回收内存
        velocityTracker.clear();
        velocityTracker.recycle();
    }

    /**
     * 获取滑动的最小距离
     */
    private void getTouchSlop2() {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        int touchSlop = configuration.getScaledTouchSlop();
        Log.d(TAG,"getTouchSlop2()" + "touchSlop = " + touchSlop);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure()");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG,"onLayout()");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw()");
        canvas.drawColor(Color.RED);
    }


    /**
     * 3.1.3
     * MotionEvent和TouchSlop
     *
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //返回相对于当前view左上角的x，y坐标
        event.getX();
        event.getY();
        //返回相对于手机屏幕左上角的x，y坐标
        event.getRawX();
        event.getRawY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"ACTION_DOWN()");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"ACTION_MOVE()");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"ACTION_UP()");
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 3.1.2
     *View的坐标位置
     */


    private void getCoordinatePosition() {

        //左上角坐标
        getLeft();
        getTop();

        //右下角坐标
        getRight();
        getBottom();

        //view的左上角坐标
        getX();
        getY();

        //view左上角左边相对于父容器的偏移量
        getTranslationX();
        getTranslationY();

        //View平移过程中，top和left不回改变，改变的是x,y,translationgX,translationY
    }




}
