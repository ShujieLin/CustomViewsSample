package com.example.customviewsample.car_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.customviewsample.R;

public class Main11Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CarView(this));
    }
}