package com.shujiel.eventdistribution;

import java.util.ArrayList;
import java.util.List;

public class ViewGroup extends View{



    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    List<View> childList = new ArrayList<>();
    private View[] mChildren =  new View[0];


    public boolean dispatchTouchEvent(MotionEvent event){
        boolean handled = false;//是否处理事件
        boolean intercepted = onInterceptTouchEvent(event);
        int actionMasked = event.getActionMasked();
        //假如不拦截并且evnet不等于ACTION_CANCEL
        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercepted){
            //ACTION_DOWN事件
            if (actionMasked == MotionEvent.ACTION_DOWN){

                final View[] children = mChildren;

                for (int i = children.length -1;i >= 0;i --){
                    View child = mChildren[i];

                    //假如View不能够接收事件，执行下一次循环
                    if (!child.isContainer(event.getX(),event.getY())){
                        continue;
                    }

                    //假如View能够接收事件。
                    if (dispatchTransformedTouchEvent(event,child)){

                    }




                }
            }
        }

        return handled;
    }

    private boolean dispatchTransformedTouchEvent(MotionEvent event, View child) {

        return false;
    }

    private boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }


    private static final class TouchTarget{
        public View child;

        /**
         *
         */
        private static TouchTarget sRecycleBin;
        /**
         *
         */
        public TouchTarget next;

        /**
         * 回收池锁
         */
        private static final Object sRecycleLock = new Object[0];

        /**
         * 回收池数量
         */
        private static int sRecycledCount;

        public static TouchTarget obtain(View child){
            TouchTarget target;
            synchronized (sRecycleLock){
                if (sRecycleBin == null){
                    target = new TouchTarget();
                }else {
                    target = sRecycleBin;
                }
                sRecycleBin = target.next;
                sRecycledCount --;
                target.next = null;
            }
            target.child = child;
            return target;
        }

    }

}
