package com.example.customviewsample.big_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.InputStream;

public class BigView extends View {
    private BitmapFactory.Options mOptions;
    private Rect mRect;
    private int mImageWidth;
    private int mImageHeight;

    public BigView(Context context) {
        super(context,null);
    }



    public BigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public BigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //bigview的成员变量
        mRect = new Rect();
        //内存复用
        mOptions = new BitmapFactory.Options();


    }



    public void setImage(InputStream is){
        //获取图片的信息，不能将整张图片加载进内存
        mOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(is,null,mOptions);
        mImageWidth = mOptions.outWidth;
        mImageHeight = mOptions.outHeight;

        mOptions.inMutable = true;
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        mOptions.inJustDecodeBounds = false;



    }


}
