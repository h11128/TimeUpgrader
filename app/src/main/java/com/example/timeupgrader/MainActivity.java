package com.example.timeupgrader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;
    ProgressBar progressBar;
    Button exit;
    Button signout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendFinishActivityBroadcast(getApplicationContext());
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate() called!!!");

        toolbar = findViewById(R.id.toolbarProfile);
        progressBar = findViewById(R.id.progressBarProfile);
        signout = findViewById(R.id.btnSignout);
        exit = findViewById(R.id.btnExit);
        fab = findViewById(R.id.addactivity);

        toolbar.setTitle("TimeUpgrader");

        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewActivity.class));
                finish();
            }
        });
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

    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("Exit");
        normalDialog.setMessage("Are you sure to exit?");
        normalDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MainActivity.this.finish();
                    }
                });
        normalDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        normalDialog.create().show();
    }
}