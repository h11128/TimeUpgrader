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
    private Context mContext;
    private NotificationManager notificationManager;
    private Notification.Builder mBuilder;
    private Notification notification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(mContext);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent newIntent = new Intent();
        newIntent.setClass(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext,0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = mBuilder.setContentTitle(intent.getStringExtra("title"))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("")
                .setContentText(intent.getStringExtra("contentText"))
                .setContentIntent(contentIntent)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.DEFAULT_LIGHTS;
        notification.flags |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
        return START_REDELIVER_INTENT;
    }
}
