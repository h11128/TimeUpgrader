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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context mContext;
    public List<SingleAct> mData;
    private TaskDatabaseHelper dbHelper;
    private FireBaseHelper fbHelper;
    private SharedPreferences settings;

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
        holder.startTime.setText("Start time: " + sdf1.format(act.getStartTime()));
        holder.location.setText("Location: " + (act.getLocation() == null ? "N/A" : act.getLocation()));
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

        settings = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);

        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (act.getStatus() == SingleAct.SET || act.getStatus() == SingleAct.START) {
                    Date date = new Date();
                    if (act.getStartTime() <= date.getTime()) {
                        boolean reminder = settings.getBoolean("checkReminder", true);
                        if (reminder) {
                            final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                            dialog.setTitle("Check your activity");
                            dialog.setMessage("Are you sure to complete your activity?");
                            LayoutInflater inflater = LayoutInflater.from(mContext);
                            View view = inflater.inflate(R.layout.checkbox_do_not_ask, null);
                            final CheckBox checkBox = view.findViewById(R.id.checkboxDoNotAsk);
                            dialog.setView(view);
                            dialog.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (checkBox.isChecked()) {
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putBoolean("checkReminder", false);
                                        editor.apply();
                                    }
                                    act.setStatus(SingleAct.END);
                                    mData.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                    dbHelper.updateActivityStatus(act.getId(), SingleAct.END);
                                    fbHelper.updateActStatus(act, SingleAct.END);
                                    User u = User.getCurrentUser();
                                    if (u != null && u.getEmail().equals(Email.getCurrentEmail().getEmail())) {
                                        u.addPoint(act.getRewardPoint());
                                    }
                                    long prePoint = dbHelper.getPointByEmail(act.getOwner());
                                    if (prePoint != -1) {
                                        dbHelper.updatePointByEmail(act.getOwner(), prePoint + act.getRewardPoint());
                                        fbHelper.updatePointByEmail(act.getOwner(), prePoint + act.getRewardPoint());
                                    }
                                    dialog.dismiss();
                                    Toast.makeText(mContext, "Activity completed! You got " + act.getRewardPoint() + " reward points.", Toast.LENGTH_LONG).show();

                                }
                            });
                            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (checkBox.isChecked()) {
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putBoolean("checkReminder", false);
                                        editor.apply();
                                    }
                                    dialog.dismiss();
                                }
                            });
                            dialog.create().show();
                        }
                        else {
                            act.setStatus(SingleAct.END);
                            mData.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            dbHelper.updateActivityStatus(act.getId(), SingleAct.END);
                            fbHelper.updateActStatus(act, SingleAct.END);
                            Toast.makeText(mContext, "Activity completed! You got " + act.getRewardPoint() + " reward points.", Toast.LENGTH_LONG).show();
                        }
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
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new MainViewHolder(v);
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout root;
        public TextView name;
        public TextView description;
        public TextView status;
        public TextView startTime;
        public TextView location;
        public Button complete;
        public Button delete;

        public MainViewHolder(View v) {
            super(v);
            root = itemView.findViewById(R.id.list_root);
            name = itemView.findViewById(R.id.aName);
            description = itemView.findViewById(R.id.aDescription);
            status = itemView.findViewById(R.id.aStatus);
            startTime = itemView.findViewById(R.id.aStartTime);
            location = itemView.findViewById(R.id.aLocation);
            complete = itemView.findViewById(R.id.aComplete);
            delete = itemView.findViewById(R.id.aDelete);
        }
    }
}
