package com.example.timeupgrader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FocusActivity extends AppCompatActivity {

    boolean starting;
    boolean locked;
    private FocusActivity.ScreenBroadcastReceiver mScreenReceiver;
    private FocusFragment fragment;
    private ToFragmentListener mToFragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        starting = true;
        locked = false;

        mScreenReceiver = new ScreenBroadcastReceiver();
        startScreenBroadcastReceiver();

        FragmentManager fm = getSupportFragmentManager();
        fragment = (FocusFragment) fm.findFragmentById(R.id.container_focus);
        if (fragment == null) {
            fragment = new FocusFragment();
            fm.beginTransaction().add(R.id.container_focus, fragment).commit();
        }
        mToFragmentListener = fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!starting && !locked) {
            showInterruptDialog();
        }
        starting = false;
        locked = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mScreenReceiver != null) {
            unregisterReceiver(mScreenReceiver);
        }
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;
        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                // screen off
                locked = true;
            }
            /*else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                // screen on
            }
            else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                // screen unlocked
            }*/
        }
    }

    private void startScreenBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenReceiver, filter);
    }

    private void showInterruptDialog(){
        if (mToFragmentListener != null) {
            mToFragmentListener.onFocusInterrupted("");
        }
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("Your focus has been interrupted");
        normalDialog.setMessage("You have been distracted. The focus will finish and you will not get any reward point. Be focused next time!");
        normalDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog ad = normalDialog.create();
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        ad.show();
    }

    public interface ToFragmentListener {
        void onFocusInterrupted(String message);
    }
}
