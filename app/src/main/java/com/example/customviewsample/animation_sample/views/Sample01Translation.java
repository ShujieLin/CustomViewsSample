package com.example.customviewsample.animation_sample.views;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.example.customviewsample.R;
import com.example.customviewsample.animation_sample.Utils;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.customviewsample.animation_sample.Utils.dpToPixel;

public class Sample01Translation extends RelativeLayout {
    private Button mAnimateBt;
    private ImageView mImageView;
    private int mTranslationState;
    /**
     * api21以上才支持z轴动画
     */
    int mTranslationStateCount = SDK_INT > Build.VERSION_CODES.LOLLIPOP ? 6 : 4;

    public Sample01Translation(Context context) {
        super(context);
    }

    public Sample01Translation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample01Translation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mAnimateBt = (Button) findViewById(R.id.animateBt);
        mImageView = (ImageView) findViewById(R.id.imageView);

        if (SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            mImageView.setOutlineProvider(new MusicOutlineProvider());
        }


        mAnimateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mTranslationState) {
                    case 0:
                        mImageView.animate().translationX(dpToPixel(100));
                        break;
                    case 1:
                        mImageView.animate().translationX(0);
                        break;
                    case 2:
                        mImageView.animate().translationY(dpToPixel(50));
                        break;
                    case 3:
                        mImageView.animate().translationY(0);
                        break;
                    case 4:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mImageView.animate().translationZ(dpToPixel(15));
                        }
                        break;
                    case 5:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mImageView.animate().translationZ(0);
                        }
                        break;
                }
                mTranslationState++;
                if (mTranslationState == mTranslationStateCount) {
                    mTranslationState = 0;
                }
            }
        });
    }

    /**
     * 为音乐图标设置三角形的 Outline。
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    class MusicOutlineProvider extends ViewOutlineProvider {
        Path path = new Path();
        {
            path.moveTo(0, dpToPixel(10));
            path.lineTo(dpToPixel(7), dpToPixel(2));
            path.lineTo(dpToPixel(116), dpToPixel(58));
            path.lineTo(dpToPixel(116), dpToPixel(70));
            path.lineTo(dpToPixel(7), dpToPixel(128));
            path.lineTo(0, dpToPixel(120));
            path.close();
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setConvexPath(path);
        }

    }

}
