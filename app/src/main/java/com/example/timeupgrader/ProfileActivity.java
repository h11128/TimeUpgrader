package com.example.timeupgrader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressBar progressBar;
    Button exit;
    Button signout;
    private static final String TAG = ProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (MainActivity.mainActivity != null) MainActivity.mainActivity.finish();
        if (LoginActivity.loginActivity != null) LoginActivity.loginActivity.finish();
        Log.i(TAG, "onCreate() called!!!");

        toolbar = findViewById(R.id.toolbarProfile);
        progressBar = findViewById(R.id.progressBarProfile);
        exit = findViewById(R.id.btnExit);
        signout = findViewById(R.id.btnSignout);

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

                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
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

    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ProfileActivity.this);
        normalDialog.setTitle("Exit");
        normalDialog.setMessage("Are you sure to exit?");
        normalDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ProfileActivity.this.finish();
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
