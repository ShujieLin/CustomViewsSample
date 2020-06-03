package com.example.customviewsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customviewsample.R;

import java.util.ArrayList;
import java.util.List;

public class ViewsListAdapter extends RecyclerView.Adapter<ViewsListAdapter.MyViweHolder>{

    private List<String> mViewsName = new ArrayList<>();
    private Context mContext;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;


    public ViewsListAdapter(Context context, List<String> viewsName){
            mContext = context;
            mViewsName = viewsName;
    }

    @NonNull
    @Override
    public MyViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViweHolder holder = new MyViweHolder(LayoutInflater.from(mContext).inflate(R.layout.item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViweHolder holder, int position) {
        holder.mTextView.setText(mViewsName.get(position));

        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mViewsName.size();
    }

    public class MyViweHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public MyViweHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv);

        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int postion);
        void onItemLongClick(View view,int postion);
    }

}
