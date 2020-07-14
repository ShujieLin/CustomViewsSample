package com.shujiel.eventdistribution.listener;

import com.shujiel.eventdistribution.MotionEvent;
import com.shujiel.eventdistribution.View;


public interface OnTouchListener {
    boolean onTouch(View v, MotionEvent event);
}
