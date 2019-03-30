package com.example.timeupgrader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private Context mContext;
    private List<SingleAct> mData;
    private TaskDatabaseHelper dbHelper;
    private FireBaseHelper fbHelper;

    MainAdapter(List<SingleAct> data, Context context) {
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
        // SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        holder.startTime.setText("Start: " + sdf1.format(act.getStartTime()));
        if (act.getStatus() == SingleAct.SET || act.getStatus() == SingleAct.START) {
            holder.complete.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
        }
        else if (act.getStatus() == SingleAct.END) {
            holder.complete.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
        }
        else {
            holder.complete.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }

        /*holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });*/

        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (act.getStatus() == SingleAct.SET || act.getStatus() == SingleAct.START) {
                    Date date = new Date();
                    if (act.getStartTime() <= date.getTime()) {
                        act.setStatus(SingleAct.END);
                        mData.remove(position);
                        notifyItemRemoved(position);
                        dbHelper.updateActivityStatus(act.getId(), SingleAct.END);
                        fbHelper.updateActStatus(act, SingleAct.END);
                    }
                    else {
                        Toast.makeText(mContext, "Too early to start!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyItemRemoved(position);
                dbHelper.updateActivityStatus(act.getId(), SingleAct.DELETE);
                fbHelper.updateActStatus(act, SingleAct.DELETE);
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
        public Button complete;
        public Button delete;

        public MainViewHolder(View v) {
            super(v);
            root = itemView.findViewById(R.id.list_root);
            name = itemView.findViewById(R.id.aName);
            description = itemView.findViewById(R.id.aDescription);
            status = itemView.findViewById(R.id.aStatus);
            startTime = itemView.findViewById(R.id.aStartTime);
            complete = itemView.findViewById(R.id.aComplete);
            delete = itemView.findViewById(R.id.aDelete);
        }
    }
}
