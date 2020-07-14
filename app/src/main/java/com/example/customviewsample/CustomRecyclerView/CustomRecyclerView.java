package com.example.customviewsample.CustomRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.example.customviewsample.R;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerView extends ViewGroup {

    private static final String TAG = CustomRecyclerView.class.getSimpleName();
    private Adapter adapter;
    /**
     * 当前显示的View
     */
    private List<View> viewList;
    /**
     * 当前滑动的y值
     */
    private int currenY;
    /**
     * 行数
     */
    private int rowCount;
    /**
     * RecyclerView的第一个可见元素
     */
    private int firstVisableRow;
    /**
     * y偏移量
     */
    private int scrollY;

    /**
     * 初始化，第一屏最慢
     */
    private boolean needRelayout;

    /**
     * 容器RecyclerView的高度
     */
    private int height;

    /**
     * 容器宽度
     */
    private int width;
    /**
     * item 高度
     */
    private int[] heights;

    /**
     * 最小滑动距离
     */
    private int touchSlop;


    /**
     * 回收池
     */
    private Recycler recycler;




    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        if (adapter != null){
            recycler = new Recycler(adapter.getViewTpyeCount());
            scrollY = 0;
            firstVisableRow = 0;
            needRelayout = true;
            //触发onMeasure方法和onLayout方法
            requestLayout();
        }
    }



    public CustomRecyclerView(Context context) {
        super(context);

    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int h = 0;

        if (adapter != null){

            this.rowCount = adapter.getCount();
            heights = new int[rowCount];
            for (int i = 0;i < heights.length;i ++){
                heights[i] = adapter.getHeight(i);

            }
        }


        int tempHeight = sumArrayHeight(heights,0,heights.length);
        h = Math.min(heightSize,tempHeight);
        setMeasuredDimension(widthSize,h);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }


    /**
     * 计算总高度
     * @param heights
     * @param firstIndex
     * @param count
     * @return
     */
    private int  sumArrayHeight(int[] heights, int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex;i < count;i ++){
            sum += heights[i];
        }
        return sum;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //需要重新layout的时候
        if (needRelayout || changed){
            needRelayout = false;
            //清空
            viewList.clear();
            removeAllViews();
            if (adapter != null){
                width = r - l;
                height = b - t;
                int left,top = 0,right,bottom;
                //绘制第一屏view
                //循环获取每个item 的参数
                for (int i = 0;i < rowCount && top < height;i ++){
                    right = width;
                    //获取第i个item的bottom值
                    bottom = top + heights[i];
                    View view = makeAndSetup(i,0,top,width,bottom);
                    viewList.add(view);

                    //获取第(i + 1)的item的top值
                    top = bottom;
                }
            }
        }

    }

    /**
     * 新建每个item的View，并且进行摆放
     * @param row
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    private View makeAndSetup(int row, int left, int top, int right, int bottom) {
        View view = obtaionView(row,right - left,bottom - top);
        view.layout(left,top,right,bottom);
        return view;
    }


    /**
     *
     * @param row 行
     * @param width
     * @param height
     * @return
     */
    private View obtaionView(int row, int width, int height) {

        //获取当前item的type
        int itemTpye = adapter.getItemView(row);
        //在回收池进行查找是否含有该类型的item
        View recyclerView = recycler.get(itemTpye);
        View view = null;
        //假如回收池含有该类型的item
        if (recyclerView == null){
            view = adapter.onCreateViewHolder(row,recyclerView,this);
            if (view == null){
                throw new RuntimeException("onCreateViewHolder 必须填充布局");
            }
        }
        //假如没有则从onBinderViewHolder获取
        else {
            view = adapter.onBinderViewHolder(row,recyclerView,this);
        }


        //给item添加tag标记，假如item被滑动出屏幕外，需要通过tag标记获取
        view.setTag(R.id.tag_type_view,itemTpye);
        //测量item，获取宽高
        view.measure(
                MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY)
        );
        //把item添加到RecyclerView容器里
        addView(view,0);
        return view;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //是否拦截
        boolean intercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:  //down事件不消耗事件
                currenY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                //move事件则消耗事件
                int y2 = Math.abs(currenY - (int)ev.getRawY());
                if (y2 > touchSlop){
                    intercept = true;
                }
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int y2 = (int) event.getRawY();
                //down坐标 - move坐标 = 滑动距离
                //上滑为正数，下滑为负数
                int diffY = currenY - y2;
                scrollBy(0,diffY);
        }
        return super.onTouchEvent(event);
    }


    /**
     * scrollBy只会引起canvas的位置变化，不会引起item变化
     * 这里，需要处理的逻辑有：
     * 1.上滑移除item
     * 2.上滑加载item
     * 3.下滑移除item
     * 4.下滑加载item
     * @param x
     * @param y
     */
    @Override
    public void scrollBy(int x, int y) {


        scrollY += y;

        //判断边界
        scrollY = scrollBounds(scrollY);

        if (scrollY > 0){//上滑
            //假如上滑速度很快，一下子把几个item移除
            //循环移除item
            while (scrollY > heights[firstVisableRow]){
                //移除第一个view
                removeView(viewList.remove(0));

                //假如用户滑动很快，需要循环移除好几个item
                scrollY -= heights[firstVisableRow];//移除第一个item后剩下的scollY的值
                firstVisableRow ++;//第一个item的下标加一
            }

            //加载item
            //判断什么时候需要加载item：当整体高度 - 滑动高度 <= 屏幕高度,加载item
            while (getFillHeight() < height){
                //需要加载的view的index
                int addLastIndex = firstVisableRow + viewList.size();
                View view = obtaionView(addLastIndex,width,heights[addLastIndex]);
                viewList.add(viewList.size(),view);
            }


        }else if (scrollY < 0){//下滑

            //下滑加载item
            while (scrollY < 0){
                //加载第一个item
                int firstAddRow = firstVisableRow - 1;
                View view = obtaionView(firstAddRow,width,heights[firstAddRow]);
                viewList.add(0,view);

                //假如用户滑动很快，需要加载好几个item
                firstVisableRow --;//下标减一
                scrollY += heights[firstVisableRow + 1];
            }

            //下滑移除
            //当总高度 - 滑动距离 - 最后一个item高度 >= 屏幕高度
            while (sumArrayHeight(heights, firstVisableRow, viewList.size()) - scrollY - heights[firstVisableRow + viewList.size() - 1] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }


        }else {

        }

        //重新摆放item位置
        repositionViews();

    }

    private int scrollBounds(int scrollY) {
        if (scrollY > 0){//上滑
//            scrollY = Math.min(scrollY sumArray());
            scrollY = Math.min(scrollY,sumArrayHeight(heights,firstVisableRow,heights.length - firstVisableRow) - height);
        }else {
//            scrollY = 0;
            Log.d(TAG, "scrollBounds: " + "处理边界前 : scrollY = " + scrollY + " - sumArrayHeight(heights,0,firstVisableRow) = " + (- sumArrayHeight(heights,0,firstVisableRow)));
            scrollY = Math.max(scrollY ,- sumArrayHeight(heights,0,firstVisableRow));
            Log.d(TAG, "scrollBounds: " + "scrollY = " + scrollY);
        }
        return scrollY;
    }

    /**
     * 用历遍的方式重新layout 每个item的位置
     */
    private void repositionViews() {
        int left,top,right,bottom,i;
        top = -scrollY;
        i = firstVisableRow;
        for (View view : viewList){
            bottom = top + heights[i ++];
            view.layout(0,top,width,bottom);
            top = bottom;//下一个item的top = 上一个item的bottom
        }
    }

    /**
     * 获取view整体高度 - 滑动高度的值
     * @return
     */
    private int getFillHeight() {
        return sumArrayHeight(heights,firstVisableRow,viewList.size()) - scrollY;
    }


    @Override
    public void removeView(View view) {
        super.removeView(view);
        int key = (int) view.getTag(R.id.tag_type_view);
    }

    interface Adapter{
        View onCreateViewHolder(int position,View converView,ViewGroup parent);
        View onBinderViewHolder(int position,View converView,ViewGroup parent);

        int getItemView(int row);

        /**
         * Item类型数量
         * @return
         */
        int getViewTpyeCount();
        int getCount();
        int getHeight(int index);


    }
}
