package com.example.timeupgrader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Fragment[] fragments;
    private int lastFragment;
    Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendFinishActivityBroadcast(getApplicationContext());
        setContentView(R.layout.activity_main);
        Log.i(TAG, "in Main onCreate() called!!!");
        initFragment();

        toolbar = findViewById(R.id.toolbarProfile);
        progressBar = findViewById(R.id.progressBarProfile);
        toolbar.setTitle("TimeUpgrader");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_top_1:
                        startActivity(new Intent(MainActivity.this, AccountActivity.class));
                        break;
                    case R.id.item_top_2:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.item_top_3:
                        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                    case R.id.item_top_4:
                        showNormalDialog();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "in Main onStart() called!!!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "in Main onPause() called!!!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "in Main onResume() called!!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "in Main onStop() called!!!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "in Main onRestart() called!!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "in Main onDestroy() called!!!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "in Main CreateOptionsMenu called!!!");
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initFragment() {
        BottomNavigationView bottomNavigationView;
        MainFragment fragment1 = new MainFragment();
        AddFocusFragment fragment2 = new AddFocusFragment();
        // GroupFragment fragment3 = new GroupFragment();
        HistoryFragment fragment4 = new HistoryFragment();
        MoreFragment fragment5 = new MoreFragment();
        fragments = new Fragment[] {fragment1, fragment2, /*fragment3,*/ fragment4, fragment5};
        lastFragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, fragment1).show(fragment1).commit();
        bottomNavigationView = findViewById(R.id.bnv_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_bottom_1: {
                    if(lastFragment != 0) {
                        switchFragment(lastFragment, 0);
                        lastFragment = 0;
                    }
                    return true;
                }
                case R.id.item_bottom_2: {
                    if(lastFragment != 1) {
                        switchFragment(lastFragment, 1);
                        lastFragment = 1;
                    }
                    return true;
                }
                /*case R.id.item_bottom_3: {
                    if(lastFragment != 2) {
                        switchFragment(lastFragment, 2);
                        lastFragment = 2;
                    }
                    return true;
                }*/
                case R.id.item_bottom_4: {
                    if(lastFragment != 2) {
                        switchFragment(lastFragment, 2);
                        lastFragment = 2;
                    }
                    return true;
                }
                case R.id.item_bottom_5: {
                    if(lastFragment != 3) {
                        switchFragment(lastFragment, 3);
                        lastFragment = 3;
                    }
                    return true;
                }
            }
            return false;
        }
    };

    private void switchFragment(int last, int current) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[last]);
        if(!fragments[current].isAdded()) transaction.add(R.id.container_main,fragments[current]);
        transaction.show(fragments[current]).commitAllowingStateLoss();
    }

    public static void sendFinishActivityBroadcast(Context context) {
        context.sendBroadcast(new Intent(SignupActivity.RECEIVER_ACTION_FINISH));
        context.sendBroadcast(new Intent(LoginActivity.RECEIVER_ACTION_FINISH));
    }

    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("Exit");
        normalDialog.setMessage("Are you sure to exit?");
        normalDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
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
