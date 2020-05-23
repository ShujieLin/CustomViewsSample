package com.example.customviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        ValueAnimator animator = ValueAnimator.ofInt(0,1).setDuration(1000);





    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG,"onTouchEvent()" + event.getAction() + " isConsumed = " + dispatchTouchEvent(event));
        Log.d(TAG,"onTouchEvent()" + event.getAction() + " isConsumed = " );
        return true;
    }

}
