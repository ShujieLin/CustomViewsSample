package com.example.customviewsample.skin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.customviewsample.R;
import com.netease.skin.library.SkinActivity;
import com.netease.skin.library.utils.PreferencesUtils;

public class Main17Activity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main17);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        boolean isNight = PreferencesUtils.getBoolean(this,"isNight");
        if (isNight){
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }



    public void dayOrNight(View view){
        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (uiMode){
            case Configuration.UI_MODE_NIGHT_NO:
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                PreferencesUtils.putBoolean(this,"isNight",true);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                PreferencesUtils.putBoolean(this,"isNight",false);
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}