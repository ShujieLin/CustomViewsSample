package com.example.customviewsample.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.customviewsample.R;

public class Main17Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GradientLayout(this));
    }
}
