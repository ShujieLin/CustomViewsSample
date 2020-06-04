package com.example.customviewsample.SlideTab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SlideTab extends View {
    /**
     * 字体大小
     */
    private int mTextSize;
    /**
     * 文本颜色
     */
    private int mColorTextDef;
    /**
     * 线和圈颜色
     */
    private int mColorDef;
    /**
     * 选中字体和圆圈颜色
     */
    private int mColorSelected;
    /**
     * 基准线高度
     */
    private int mLineHight;
    /**
     * 圆圈高度/直径
     */
    private int mCircleHeight;
    /**
     * 选中圆环的笔锋
     */
    private int mCircleSelStroke;
    /**
     * 圆圈和文字的距离
     */
    private int mMarginTop;
    /**
     * tab文字
     */
    private String[] tabNames;

    //需要计算的对象和变量
    /**
     * 每段横线长度
     */
    private float splitLength;
    /**
     * 文本绘制Y轴坐标
     */
    private int textSartY;
    /**
     * 文本测量结果list
     */
    private List<Rect> mBounds;

    /**
     * 选取选中序号
     */
    private int selectedIndex = 0;

    /**
     * 文字画笔
     */
    private Paint mTextPaint;
    /**
     * 线条画笔
     */
    private Paint mLinePaint;
    /**
     * 圆环画笔
     */
    private Paint mCirclePaint;
    /**
     * 选中的圆圈画笔
     */
    private Paint mCircleSelPaint;


    public SlideTab(Context context) {
        this(context,null);
    }

    public SlideTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    private void init() {
        tabNames = new String[]{"TAB1","TAB2","TAB3","TAB4"};

        mColorTextDef = Color.BLACK;
        mColorSelected = Color.GREEN;
        mColorDef = Color.GRAY;
        mTextSize = 20;

        mLineHight = 5;
        mCircleHeight = 20;
        mCircleSelStroke = 10;
        mMarginTop = 50;

        mLinePaint = new Paint();
        mCirclePaint = new Paint();
        mTextPaint = new Paint();
        mCircleSelPaint = new Paint();

        mLinePaint.setColor(mColorDef);
        mLinePaint.setStyle(Paint.Style.FILL);//填充
        mLinePaint.setStrokeWidth(mLineHight);//笔宽像素
        mLinePaint.setAntiAlias(true);

        mCirclePaint.setColor(mColorDef);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setStrokeWidth(1);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mColorSelected);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);

        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColorTextDef);
        mLinePaint.setAntiAlias(true);

        measureText();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initConstant();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        //画线
        canvas.drawLine(mCircleHeight / 2,mCircleHeight / 2,getWidth() - mCircleHeight / 2,mCircleHeight / 2,mLinePaint);

        float centerY = mCircleHeight / 2;
        for (int i = 0;i < tabNames.length;i ++){
            //每个圆的圆心x坐标
            float centerX = mCircleHeight / 2 + (i * splitLength);
            //画圆
            canvas.drawCircle(centerX,centerY,mCircleHeight / 2,mCirclePaint);

            //画文本
            float startX = 0;
            if (i == 0){
                startX = 0;
            }else if (i == tabNames.length - 1){
                startX = getWidth() - mBounds.get(i).width();
            }else {
                startX = centerX - (mBounds.get(i).width() / 2);
            }

            //画选中效果,选中后，字体变绿色，圆圈变圆环，圆环变绿色
            if (selectedIndex == i){
                //画选中的蓝色圆圈
                mCirclePaint.setStrokeWidth(mCircleSelStroke);
                mCirclePaint.setStyle(Paint.Style.STROKE);
                mCirclePaint.setColor(mColorSelected);
                canvas.drawCircle(centerX,centerY,(mCircleHeight - mCircleSelStroke) / 2,mCirclePaint);
                mTextPaint.setColor(mColorSelected);
                canvas.drawText(tabNames[i],startX,textSartY,mTextPaint);
            }

//            canvas.drawText(tabNames[i],startX,textSartY,mTextPaint);
        }
    }


    /**
     * 测量每段横线长度
     * 测量文本y轴坐标
     */
    private void initConstant() {
        int lineLength = getWidth() - getPaddingLeft() - getPaddingRight() - mCircleHeight;
        splitLength = lineLength / (tabNames.length - 1);
        textSartY = mCircleHeight + mMarginTop + getPaddingTop();
    }



    /**
     * 测量textview，获取textview的buound
     */
    private void measureText() {
        mBounds = new ArrayList<>();
        for (String name : tabNames){
            Rect mBound = new Rect();
            mTextPaint.getTextBounds(name,0,name.length(),mBound);
            mBounds.add(mBound);
        }
    }

}
