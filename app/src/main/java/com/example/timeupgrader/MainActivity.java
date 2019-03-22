package com.example.timeupgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendFinishActivityBroadcast(getApplicationContext());
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate() called!!!");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container_main);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction().add(R.id.container_main, fragment).commit();
        }
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
        super.onDestroy();
        Log.i(TAG, "onDestroy() called!!!");
    }

    public static void sendFinishActivityBroadcast(Context context) {
        context.sendBroadcast(new Intent(SignupActivity.RECEIVER_ACTION_FINISH));
        context.sendBroadcast(new Intent(LoginActivity.RECEIVER_ACTION_FINISH));
    }
}
