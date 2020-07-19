package com.example.customviewsample.animation_sample;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.example.customviewsample.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity13 extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    List<PageModel> mPageModels = new ArrayList<>();

    {
        mPageModels.add(new PageModel(R.layout.sample_translation,R.string.title_translation));
        mPageModels.add(new PageModel(R.layout.sample_rotation,R.string.title_rotation));
        mPageModels.add(new PageModel(R.layout.sample_scale,R.string.title_scale));
        mPageModels.add(new PageModel(R.layout.sample_alpha,R.string.title_alpha));
        mPageModels.add(new PageModel(R.layout.sample_multi_properties,R.string.title_multi_properties));
        mPageModels.add(new PageModel(R.layout.sample_duration,R.string.title_alpha));
        mPageModels.add(new PageModel(R.layout.sample_interpolator,R.string.title_interpolator));
        mPageModels.add(new PageModel(R.layout.sample_object_anomator,R.string.title_object_animator));
        mPageModels.add(new PageModel(R.layout.sample_argb_evaluator,R.string.titile_argb_evaluator));
        mPageModels.add(new PageModel(R.layout.sample_hsv_evaluator,R.string.title_hsv_evaluatro));
        mPageModels.add(new PageModel(R.layout.sample_of_object,R.string.titile_of_object));
        mPageModels.add(new PageModel(R.layout.property_values_holder_layout,R.string.title_property_values_holder));
        mPageModels.add(new PageModel(R.layout.sample_animator_set,R.string.title_animator_set));
        mPageModels.add(new PageModel(R.layout.sample_keyframe,R.string.titile_keyframe));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);

        init();
    }

    private void init() {
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = mPageModels.get(position);
                return PageFragment.newInstance(pageModel.layoutRes);
            }

            @Override
            public int getCount() {
                return mPageModels.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return getString(mPageModels.get(position).titleRes);
            }
        });

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private class PageModel {
        @LayoutRes
        int layoutRes;
        @StringRes
        int titleRes;

        PageModel(@LayoutRes int sampleLayoutRes, @StringRes int titleRes) {
            this.layoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
        }
    }

}