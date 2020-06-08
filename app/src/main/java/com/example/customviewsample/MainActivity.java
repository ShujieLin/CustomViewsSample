package com.example.customviewsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.customviewsample.grid.PagerActivity;
import com.example.customviewsample.pager.Activity3;
import com.example.customviewsample.slidetab.SlideTabActivity;
import com.example.customviewsample.simple_custom_layout.CustomLinearLayoutViewActivity;
import com.example.customviewsample.adapter.ViewsListAdapter;
import com.example.customviewsample.view.ItemDecoration;

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
        mList.add("自定义布局view");
        mList.add("SldeTab");
        mList.add("Grid");
        mList.add("Pager");
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new ItemDecoration(this,ItemDecoration.VERTICAL_LIST));
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
        Intent intent;
        switch (postion){
            case 0:
                intent = new Intent(this,CustomLinearLayoutViewActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, SlideTabActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, PagerActivity.class);
                startActivity(intent);
            case 3:
                intent = new Intent(this, Activity3.class);
                startActivity(intent);
        }

    }

    @Override
    public void onItemLongClick(View view, int postion) {

    }
}
