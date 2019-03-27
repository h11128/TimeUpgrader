package com.example.timeupgrader;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class TimeUpgraderApp extends Application {
    private final String TAG = "TimeUpgraderApp";
    private static TimeUpgraderApp sTimeUpgraderApp;
    private int mActivityCount = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        sTimeUpgraderApp = new TimeUpgraderApp();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

            @Override
            public void onActivityStarted(Activity activity) {
                mActivityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {}

            @Override
            public void onActivityPaused(Activity activity) {}

            @Override
            public void onActivityStopped(Activity activity) {
                mActivityCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

            @Override
            public void onActivityDestroyed(Activity activity) {}
        });
    }

    public static TimeUpgraderApp getInstance( ) {
        if (sTimeUpgraderApp == null)
            sTimeUpgraderApp = new TimeUpgraderApp();
        return sTimeUpgraderApp;
    }

    public int getActivityCount( ) {
        return mActivityCount;
    }
}
