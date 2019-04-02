package com.example.timeupgrader;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int alarmCount = intent.getIntExtra("alarmCount", 0);
        String name = intent.getStringExtra("actName");
        String id = intent.getStringExtra("actId");
        Log.i("alarmCount", alarmCount + "");
        Log.i("name", name);
        Log.i("id", id);
        if (alarmCount != 0 && !name.equals("") && !id.equals("")) {
            TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(context);
            FireBaseHelper fbHelper = new FireBaseHelper();
            dbHelper.updateActivityStatus(id, SingleAct.START);
            fbHelper.updateActStatusById(id, SingleAct.START);
            Intent broadcastIntent = new Intent(MainFragment.RECEIVER_ACTION_DATA_CHANGE);
            broadcastIntent.putExtra("actId", id);
            broadcastIntent.putExtra("status", SingleAct.START);
            context.sendBroadcast(broadcastIntent);
            SharedPreferences settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
            boolean notify = settings.getBoolean("notify", true);
            if (notify) {
                Intent newIntent = new Intent(context, MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent contentIntent = PendingIntent.getActivity(context, alarmCount, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify");
                Notification notification = builder.setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Time for activity!")
                        .setContentText("Your activity " + name + " has started.")
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setOngoing(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setLights(Color.GREEN, 1000, 1000)
                        .setVibrate(new long[] { 0, 700, 700, 700 })
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setCategory(Notification.CATEGORY_ALARM)
                        .build();
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("notify", "Time for activity!", NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("Your activity " + name + " has started.");
                    channel.enableLights(true);
                    channel.setLightColor(Color.GREEN);
                    channel.enableVibration(true);
                    channel.setVibrationPattern(new long[] { 0, 700, 700, 700 });
                    channel.setBypassDnd(true);
                    channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                    notificationManager.createNotificationChannel(channel);
                }
                notificationManager.notify(alarmCount, notification);
            }
        }
    }
}