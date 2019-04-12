package com.example.timeupgrader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private SplashHandler newHandler = null;

    static class SplashHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public SplashHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String savedEmail = sp.getString("email", null);
        String savedPassword = sp.getString("password", null);
        if (savedEmail != null && !savedEmail.equals("") && savedPassword != null && !savedPassword.equals("")) {
            startActivity(new Intent(SplashActivity.this, SignupActivity.class));
        }*/

        SharedPreferences mThemeprefs = getSharedPreferences(
                "theme", Context.MODE_PRIVATE);
        if (mThemeprefs!=null) {
            Integer sTheme =(int)(long) mThemeprefs.getLong("myTheme", R.style.AppTheme);
            setTheme(sTheme);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_DISPLAY_LENGTH = 2000;
        newHandler = new SplashHandler(this);
        newHandler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, SignupActivity.class));
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() called!!!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() called!!!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() called!!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() called!!!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() called!!!");
    }

    @Override
    protected void onDestroy() {
        if (newHandler != null) {
            newHandler.removeCallbacksAndMessages(null);
            newHandler = null;
        }
        super.onDestroy();
        Log.i(TAG, "onDestroy() called!!!");
    }
}
