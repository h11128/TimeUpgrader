package com.example.timeupgrader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private Context mContext;
    private List<SingleAct> mData;
    private TaskDatabaseHelper dbHelper;
    private FireBaseHelper fbHelper;

    public MainAdapter(List<SingleAct> data, Context context) {
        this.mData = data;
        this.mContext = context;
        this.dbHelper = new TaskDatabaseHelper(mContext);
        this.fbHelper = new FireBaseHelper();
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {
        final SingleAct act = getItem(position);

        holder.name.setText(act.getName());
        holder.description.setText(act.getDescription());
        holder.status.setText(act.getStatusText());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        holder.startTime.setText("Start: " + sdf1.format(act.getStartTime()));
        holder.duration.setText("Duration: " + sdf2.format(act.getDuration()));
        if (act.getStatus() == SingleAct.SET || act.getStatus() == SingleAct.PAUSE) {
            holder.start.setVisibility(View.VISIBLE);
            holder.pause.setVisibility(View.GONE);
        }
        else if (act.getStatus() == SingleAct.START) {
            holder.start.setVisibility(View.GONE);
            holder.pause.setVisibility(View.VISIBLE);
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (act.getStatus() == SingleAct.SET || act.getStatus() == SingleAct.PAUSE) {
                    holder.start.setVisibility(View.GONE);
                    holder.pause.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (act.getStatus() == SingleAct.START) {
                    holder.start.setVisibility(View.VISIBLE);
                    holder.pause.setVisibility(View.GONE);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.updateActivityStatus(act.getId(), SingleAct.DELETE);
                fbHelper.updateActStatus(act, SingleAct.DELETE);
                removeData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected SingleAct getItem(int position){
        return mData.get(position);
    }

    public void removeData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new MainViewHolder(v);
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout root;
        public TextView name;
        public TextView description;
        public TextView status;
        public TextView startTime;
        public TextView duration;
        public Button start;
        public Button pause;
        public Button delete;

        public MainViewHolder(View v) {
            super(v);
            root = itemView.findViewById(R.id.list_root);
            name = itemView.findViewById(R.id.aName);
            description = itemView.findViewById(R.id.aDescription);
            status = itemView.findViewById(R.id.aStatus);
            startTime = itemView.findViewById(R.id.aStartTime);
            duration = itemView.findViewById(R.id.aDuration);
            start = itemView.findViewById(R.id.aStart);
            pause = itemView.findViewById(R.id.aPause);
            delete = itemView.findViewById(R.id.aDelete);
        }
    }
}
