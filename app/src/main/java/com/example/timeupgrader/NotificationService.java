package com.example.timeupgrader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class NotificationService extends Service {

    private NotificationManager notificationManager;
    private Notification.Builder mBuilder;
    private Notification notification;
    private TaskDatabaseHelper dbHelper;
    private FireBaseHelper fbHelper;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(this);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        int alarmCount = intent.getIntExtra("alarmCount", 0);
        String name = intent.getStringExtra("actName");
        String id = intent.getStringExtra("actId");
        if (alarmCount != 0 && !name.equals("") && !id.equals("")) {
            Intent newIntent = new Intent(NotificationService.this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, alarmCount, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Time for activity!")
                    .setContentText("Your activity " + name + " starts now.")
                    .setContentIntent(contentIntent)
                    .build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.flags |= Notification.DEFAULT_LIGHTS;
            notification.flags |= Notification.DEFAULT_VIBRATE;
            notificationManager.notify(alarmCount, notification);
            dbHelper = new TaskDatabaseHelper(this);
            fbHelper = new FireBaseHelper();
            dbHelper.updateActivityStatus(id, SingleAct.START);
            fbHelper.updateActStatusById(id, SingleAct.START);
        }
        return START_REDELIVER_INTENT;
    }
}
