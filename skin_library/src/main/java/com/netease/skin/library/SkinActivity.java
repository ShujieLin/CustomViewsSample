package com.netease.skin.library;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import com.netease.skin.library.core.CustomAppCompatViewInflater;
import com.netease.skin.library.core.ViewsMatch;
import com.netease.skin.library.utils.ActionBarUtils;
import com.netease.skin.library.utils.NavigationUtils;
import com.netease.skin.library.utils.StatusBarUtils;

/**
 * 换肤Activity父类
 *
 * 用法：
 * 1、继承此类
 * 2、重写openChangeSkin()方法
 */
public class SkinActivity extends AppCompatActivity {

    private CustomAppCompatViewInflater viewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //获取系统的LayouInfalter,这里实际上是模仿系统源码AppCompatDelegateImpl.java中，第1008到1010行
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (openChangeSkin()) {
            //这里实际上是模仿系统源码AppCompatDelegateImpl.java中的第979行。
            //通过new一个继承于AppCompatViewInflater的子类来继承父类的属性，并且添加自己需要的属性
            if (viewInflater == null) {
                viewInflater = new CustomAppCompatViewInflater(context);
            }
            viewInflater.setName(name);
            viewInflater.setAttrs(attrs);
            return viewInflater.autoMatch();
        }
        return super.onCreateView(parent, name, context, attrs);
    }

    /**
     * @return 是否开启换肤，增加此开关是为了避免开发者误继承此父类，导致未知bug
     */
    protected boolean openChangeSkin() {
        return false;
    }

    protected void setDayNightMode(@AppCompatDelegate.NightMode int nightMode) {

        final boolean isPost21 = Build.VERSION.SDK_INT >= 21;

        getDelegate().setLocalNightMode(nightMode);

        if (isPost21) {
            // 换状态栏
            StatusBarUtils.forStatusBar(this);
            // 换标题栏
            ActionBarUtils.forActionBar(this);
            // 换底部导航栏
            NavigationUtils.forNavigation(this);
        }

        View decorView = getWindow().getDecorView();
        applyDayNightForView(decorView);
    }

    private static final String TAG = "SkinActivity";

    /**
     * 回调接口 给具体控件换肤操作
     */
    protected void applyDayNightForView(View view) {
        if (view instanceof ViewsMatch) {
            Log.d(TAG, "applyDayNightForView: view = " + view.toString());
            ViewsMatch viewsMatch = (ViewsMatch) view;
            viewsMatch.skinnableView();
        }

        if (view instanceof ViewGroup) {
            Log.d(TAG, "applyDayNightForView: view = " + view.toString());
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            //循环历遍所有子元素
            for (int i = 0; i < childCount; i++) {
                applyDayNightForView(parent.getChildAt(i));
            }
        }

    }
}
