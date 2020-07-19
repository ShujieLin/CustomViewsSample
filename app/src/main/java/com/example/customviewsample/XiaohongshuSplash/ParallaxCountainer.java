package com.example.customviewsample.XiaohongshuSplash;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.customviewsample.CustomRecyclerView.Recycler;
import com.example.customviewsample.R;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public class ParallaxCountainer extends FrameLayout implements ViewPager.OnPageChangeListener {


    private List<ParallaxFragment> fragments;
    private ParallaxPagerAdapter adatper;

    public void setIv_man(ImageView iv_man) {
        this.iv_man = iv_man;
    }

    private ImageView iv_man;

    public ParallaxCountainer(@NonNull Context context) {
        super(context);
    }

    public ParallaxCountainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxCountainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setUp(int... childIds){
        fragments = new ArrayList<>();
        for (int i = 0;i < childIds.length;i ++){
            ParallaxFragment f = new ParallaxFragment();

            Bundle args = new Bundle();
            //Fragment中需要加载的布局文件id
            args.putInt("layoutId",childIds[i]);
            f.setArguments(args);
            fragments.add(f);
        }

        ViewPager vp = new ViewPager(getContext());
        vp.setId(R.id.parallax_pager);

        Main14Activity activity = (Main14Activity) getContext();
        adatper = new ParallaxPagerAdapter(activity.getSupportFragmentManager(),fragments);
        vp.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vp.setAdapter(adatper);
        vp.setOnPageChangeListener(this);
        addView(vp,0);

        //实例化适配器

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int containerWidth = getWidth();
        ParallaxFragment outFragment = null;


        try {
            outFragment = fragments.get(position - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ParallaxFragment inFragment = null;

        try {
            inFragment = fragments.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (outFragment != null){
            //获取Fragment上所有的视图，实现动画效果
            List<View> inViews = outFragment.getParallaxViews();
            //动画
            if (inViews != null){
                for (View view : inViews){
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null){
                        continue;
                    }
                    ViewHelper.setTranslationX(view,(containerWidth - positionOffsetPixels) * tag.xIn);
                    ViewHelper.setTranslationY(view,(containerWidth - positionOffsetPixels) * tag.yIn);
                }
            }

        }

        if (inFragment != null){
            List<View> outViews = inFragment.getParallaxViews();
            if (outViews != null){
                for (View view : outViews){
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null){
                        continue;
                    }
                    //退出的fragment中view从原始位置开始向上移动，translationY应为负数
                    ViewHelper.setTranslationY(view,0 - positionOffsetPixels * tag.yOut);
                    ViewHelper.setTranslationX(view,0 - positionOffsetPixels * tag.xOut);
                }
            }
        }



    }


    @Override
    public void onPageSelected(int position) {

        if (position == adatper.getCount() - 1){
            iv_man.setVisibility(INVISIBLE);
        }else {
            iv_man.setVisibility(VISIBLE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_man.getBackground();
        switch (state){
            case ViewPager.SCROLL_STATE_DRAGGING:
                animationDrawable.start();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                animationDrawable.stop();
                break;
            default:
                break;
        }
    }
}
