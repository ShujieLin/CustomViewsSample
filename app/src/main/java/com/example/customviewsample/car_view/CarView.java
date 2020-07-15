package com.example.customviewsample.car_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customviewsample.R;

public class CarView extends View {
    private Bitmap mCarBitmap;
    private android.graphics.Path mPath;
    /**
     * 用来测量Path的类，可以理解成专门对Path每一个点获取信息的类
     * 可计算某个点的正切 余切等，采取
     */
    private PathMeasure mPathMeasure;
    private Paint mCirclePaint;
    private Paint mCarPaint;
    /**
     * 对小车操作的矩阵，小车角度需要旋转
     */
    private Matrix mCarMatrix;
    private float mDistanceRatio = 0;

    public CarView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mCarBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_car);
        mPath = new Path();
        mPath.addCircle(0,0,200,Path.Direction.CW);

        mPathMeasure = new PathMeasure(mPath,false);
        //创建画圆笔
        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(5);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.BLACK);
        //创建车笔
        mCarPaint = new Paint(Color.DKGRAY);
        mCarPaint.setStrokeWidth(2);
        mCarPaint.setStyle(Paint.Style.STROKE);
        //矩阵
        mCarMatrix = new Matrix();
    }

    public CarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.translate(width / 2,height / 2);
        //全局成员变量，重置
        mCarMatrix.reset();

        mDistanceRatio += 0.006f;
        //循环
        if (mDistanceRatio >= 1){
            mDistanceRatio = 0;
        }

        //点的坐标
        float[] pos = new float[2];
        //点的正切值
        float[] tan = new float[2];
        //距离Path起点的距离。
        //距离 = path长度 * 百分比
        float distance = mPathMeasure.getLength() * mDistanceRatio;
        mPathMeasure.getPosTan(distance,pos,tan);

        //计算小车要旋转的角度
        //tan[0]代表 cos
        //tan[1]代表 sin
        float degree = (float) (Math.atan2(tan[1],tan[0]) * 180 / Math.PI);

        //设置小车要旋转的角度和旋转的中心坐标
        mCarMatrix.postRotate(degree,mCarBitmap.getWidth() / 2,mCarBitmap.getHeight() / 2);

        //设置圆心到小车中心点的坐标
        mCarMatrix.postTranslate(pos[0] - mCarBitmap.getWidth() / 2,pos[1] - mCarBitmap.getHeight() / 2);

        //绘制
        canvas.drawPath(mPath,mCirclePaint);
        canvas.drawBitmap(mCarBitmap,mCarMatrix,mCarPaint);

        //调用onDraw
        invalidate();
    }

}
