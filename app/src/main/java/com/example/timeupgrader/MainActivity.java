package com.example.timeupgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private Fragment[] fragments;
    private int lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendFinishActivityBroadcast(getApplicationContext());
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate() called!!!");
        initFragment();
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

    private void initFragment() {
        MainFragment fragment1 = new MainFragment();
        FocusFragment fragment2 = new FocusFragment();
        GroupFragment fragment3 = new GroupFragment();
        HistoryFragment fragment4 = new HistoryFragment();
        MoreFragment fragment5 = new MoreFragment();
        fragments = new Fragment[] {fragment1, fragment2, fragment3, fragment4, fragment5};
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
                case R.id.item_bottom_3: {
                    if(lastFragment != 2) {
                        switchFragment(lastFragment, 2);
                        lastFragment = 2;
                    }
                    return true;
                }
                case R.id.item_bottom_4: {
                    if(lastFragment != 3) {
                        switchFragment(lastFragment, 3);
                        lastFragment = 3;
                    }
                    return true;
                }
                case R.id.item_bottom_5: {
                    if(lastFragment != 4) {
                        switchFragment(lastFragment, 4);
                        lastFragment = 4;
                    }
                    return true;
                }
            }
            return false;
        }
    };

    private void switchFragment(int last,int current) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[last]);
        if(!fragments[current].isAdded()) transaction.add(R.id.container_main,fragments[current]);
        transaction.show(fragments[current]).commitAllowingStateLoss();
    }

    public static void sendFinishActivityBroadcast(Context context) {
        context.sendBroadcast(new Intent(SignupActivity.RECEIVER_ACTION_FINISH));
        context.sendBroadcast(new Intent(LoginActivity.RECEIVER_ACTION_FINISH));
    }
}
