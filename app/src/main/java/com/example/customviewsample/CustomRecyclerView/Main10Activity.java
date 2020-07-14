package com.example.customviewsample.CustomRecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customviewsample.R;

public class Main10Activity extends AppCompatActivity {
    private static final String TAG = "Main10Activity";
    CustomRecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        recyclerView = findViewById(R.id.table);
        recyclerView = findViewById(R.id.table);
        recyclerView.setAdapter(new CustomRecyclerView.Adapter() {
            @Override
            public View onCreateViewHolder(int position, View converView, ViewGroup parent) {
                converView = Main10Activity.this.getLayoutInflater().inflate(R.layout.item_table,parent,false);
                TextView textView = converView.findViewById(R.id.text1);
                textView.setText("林树杰nb" + position);
                Log.i(TAG, "onCreateViewHolder: " + converView.hashCode());
                return converView;
            }

            @Override
            public View onBinderViewHolder(int position, View converView, ViewGroup parent) {
                TextView textView = converView.findViewById(R.id.text1);
                textView.setText("林树杰" + position);
                Log.i(TAG, "onBinderViewHolder: " + converView.hashCode());
                return converView;
            }

            @Override
            public int getItemView(int row) {
                return 0;
            }

            @Override
            public int getViewTpyeCount() {
                return 1;
            }

            @Override
            public int getCount() {
                return 300000;
            }

            @Override
            public int getHeight(int index) {
                return 100;
            }
        });
    }
}