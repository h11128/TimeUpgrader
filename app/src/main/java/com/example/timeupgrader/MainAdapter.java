package com.example.timeupgrader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder>{

    public static class MainHolder extends RecyclerView.ViewHolder{
        public MainHolder(View v) {
            super(v);
        }
    }

    private List<String> mDatas;
    public MainAdapter(List<String> data) {
        this.mDatas = data;
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main, parent, false);
        return new MainHolder(v);
    }
}
