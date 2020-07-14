package com.example.customviewsample.CustomRecyclerView;

import android.view.View;

import java.util.Stack;

public class Recycler {

    /**
     * 栈
     *
     */
    private Stack<View>[] views;


    /**
     * 构造函数
     * 新建views的栈
     * @param typeNumber 回收池栈的数量
     */
    public Recycler(int typeNumber){
        views = new Stack[typeNumber];
        for (int i = 0;i < typeNumber;i ++){
            views[i] = new Stack<>();
        }
    }

    public void put(View view,int type){
        views[type].push(view);
    }



    public View get(int type){
        try {
            return  views[type].pop();
        }catch (Exception e){
            return null;
        }
    }







}
