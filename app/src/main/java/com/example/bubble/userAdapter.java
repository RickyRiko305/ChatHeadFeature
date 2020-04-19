package com.example.bubble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.MyViewHolder> {

    private int mData;
    private Context mContext;

    public userAdapter (int mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public userAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.bubble_layout, viewGroup, false);
        //return null;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //public ImageView
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
