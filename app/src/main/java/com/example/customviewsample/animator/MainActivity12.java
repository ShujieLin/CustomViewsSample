package com.example.customviewsample.animator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customviewsample.R;

public class MainActivity12 extends AppCompatActivity {

    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        mButton = findViewById(R.id.btn);
    }

    public void scale(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mButton,"scaleX",2f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

}