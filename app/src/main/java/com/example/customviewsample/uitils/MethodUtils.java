package com.example.customviewsample.uitils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MethodUtils {

    /**
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampleBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();

        //开启设置，加BitmapFactory不会真正载图片，只会解析图片的原始宽高
        options.inJustDecodeBounds = true;
        //对资源进行解码
        BitmapFactory.decodeResource(res,resId,options);
        //计算缩放比例
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        //关闭设置
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res,resId,options);

    }

    /**
     * 计算出采样率inSampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
