package com.example.customviewsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.customviewsample.CustomRecyclerView.Main10Activity;
import com.example.customviewsample.XiaohongshuSplash.Main14Activity;
import com.example.customviewsample.adapter.ViewsListAdapter;
import com.example.customviewsample.animation_demo.Main6Activity;
import com.example.customviewsample.animation_sample.MainActivity13;
import com.example.customviewsample.animator.MainActivity12;
import com.example.customviewsample.asnync_task_view.Main9Activity;
import com.example.customviewsample.big_image.Main7Activity;
import com.example.customviewsample.car_view.Main11Activity;
import com.example.customviewsample.grid.PagerActivity;
import com.example.customviewsample.image_loader.Main8Activity;
import com.example.customviewsample.set_factory.Main16Activity;
import com.example.customviewsample.skin.Main17Activity;
import com.example.customviewsample.turn_gray.Main15Activity;
import com.example.customviewsample.pager.Activity3;
import com.example.customviewsample.simple_custom_layout.CustomLinearLayoutViewActivity;
import com.example.customviewsample.slidetab.SlideTabActivity;
import com.example.customviewsample.view1.Main1Activity;
import com.example.customviewsample.view5.Main5Activity;

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
        mList.add("CustomLayout");//0
        mList.add("view1");//1
        mList.add("SlideTabView");//2
        mList.add("CusGridLayout");//3
        mList.add("LSJPager");//4
        mList.add("CustomViewGroup");//5
        mList.add("属性动画demo");
        mList.add("加载大图");//7
        mList.add("ImageLoader");//8
        mList.add("AsyncTask");//9
        mList.add("自定义RecyclerView");//10
        mList.add("围绕圆心运动动画");//11
        mList.add("自定义动画框架");//12
        mList.add("给tabLayout添加动画");//13
        mList.add("小红书动画");//14
        mList.add("全部变灰！");//15
        mList.add("setFactory的使用");//16
        mList.add("换肤");//17


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
        Intent intent;
        switch (postion) {
            case 0:
                intent = new Intent(this, CustomLinearLayoutViewActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, Main1Activity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, SlideTabActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, PagerActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, Activity3.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(this, Main5Activity.class);
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(this, Main6Activity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(this, Main7Activity.class);
                startActivity(intent);
                break;
            case 8:
                intent = new Intent(this, Main8Activity.class);
                startActivity(intent);
                break;
            case 9:
                intent = new Intent(this, Main9Activity.class);
                startActivity(intent);
                break;
            case 10:
                intent = new Intent(this, Main10Activity.class);
                startActivity(intent);
                break;
            case 11:
                intent = new Intent(this, Main11Activity.class);
                startActivity(intent);
                break;
            case 12:
                intent = new Intent(this, MainActivity12.class);
                startActivity(intent);
                break;
            case 13:
                intent = new Intent(this, MainActivity13.class);
                startActivity(intent);
                break;
            case 14:
                intent = new Intent(this, Main14Activity.class);
                startActivity(intent);
                break;
            case 15:
                intent = new Intent(this, Main15Activity.class);
                startActivity(intent);
                break;
            case 16:
                intent = new Intent(this, Main16Activity.class);
                startActivity(intent);
                break;
            case 17:
                intent = new Intent(this, Main17Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int postion) {

    }

}
