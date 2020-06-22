package com.example.customviewsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.customviewsample.dragview.CustomLinearLayoutViewActivity;
import com.example.customviewsample.adapter.ViewsListAdapter;
import com.example.customviewsample.view1.Main1Activity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewsListAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private List<String> mList;
    private ViewsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("拖拽view");
        mList.add("自定义view学习");

    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter = new ViewsListAdapter(this,mList));

        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent()" + event.getAction());
        return false;
    }

    @Override
    public void onItemClick(View view, int postion) {
        Intent intent ;
        switch (postion){
            case 0:
                intent = new Intent(this, CustomLinearLayoutViewActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, Main1Activity.class);
                startActivity(intent);
                break;
            default:
        }


    }

    @Override
    public void onItemLongClick(View view, int postion) {

    }

}
