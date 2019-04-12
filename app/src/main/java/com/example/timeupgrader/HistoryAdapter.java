package com.example.timeupgrader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private Context mContext;
    private List<SingleAct> mData;
    private TaskDatabaseHelper dbHelper;
    private FireBaseHelper fbHelper;
    private SharedPreferences settings;

    HistoryAdapter(List<SingleAct> data, Context context) {
        this.mData = data;
        this.mContext = context;
        this.dbHelper = new TaskDatabaseHelper(mContext);
        this.fbHelper = new FireBaseHelper();
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, final int position) {
        final SingleAct act = getItem(position);

        holder.name.setText(act.getName());
        holder.description.setText(act.getDescription());
        holder.status.setText(act.getStatusText());
        holder.location.setText("Location: " + act.getLocation());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        holder.startTime.setText("Start time: " + sdf1.format(act.getStartTime()));

        /*holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });*/

        settings = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean reminder = settings.getBoolean("deleteReminder", true);
                if (reminder) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("Delete your activity");
                    dialog.setMessage("Are you sure to delete your activity? You can not recover deleted activity.");
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    View view = inflater.inflate(R.layout.checkbox_do_not_ask, null);
                    final CheckBox checkBox = view.findViewById(R.id.checkboxDoNotAsk);
                    dialog.setView(view);
                    dialog.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkBox.isChecked()) {
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("deleteReminder", false);
                                editor.apply();
                            }
                            mData.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            dbHelper.updateActivityStatus(act.getId(), SingleAct.DELETE);
                            fbHelper.updateActStatus(act, SingleAct.DELETE);
                            dialog.dismiss();
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkBox.isChecked()) {
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("deleteReminder", false);
                                editor.apply();
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.create().show();
                }
                else {
                    mData.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    dbHelper.updateActivityStatus(act.getId(), SingleAct.DELETE);
                    fbHelper.updateActStatus(act, SingleAct.DELETE);
                }
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
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(v);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout root;
        public TextView name;
        public TextView description;
        public TextView status;
        public TextView startTime;
        public TextView location;
        public Button delete;

        public HistoryViewHolder(View v) {
            super(v);
            root = itemView.findViewById(R.id.history_root);
            name = itemView.findViewById(R.id.hName);
            description = itemView.findViewById(R.id.hDescription);
            status = itemView.findViewById(R.id.hStatus);
            startTime = itemView.findViewById(R.id.hStartTime);
            location = itemView.findViewById(R.id.hLocation);
            delete = itemView.findViewById(R.id.hDelete);
        }
    }
}