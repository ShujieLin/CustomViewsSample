package com.example.customviewsample.XiaohongshuSplash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.customviewsample.R;

public class Main14Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main14);
        ParallaxCountainer countainer = findViewById(R.id.parallax_container);
        countainer.setUp(new int[]{
                R.layout.view_intro_1,
                R.layout.view_intro_2,
                R.layout.view_intro_3,
                R.layout.view_intro_4,
                R.layout.view_intro_5,
                R.layout.view_intro_6,
                R.layout.view_intro_7,
                R.layout.view_login
        });

        ImageView iv_man = findViewById(R.id.iv_man);
        iv_man.setBackgroundResource(R.drawable.man_run);
        countainer.setIv_man(iv_man);

    }



}
