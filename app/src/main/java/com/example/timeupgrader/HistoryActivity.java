package com.example.timeupgrader;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences mThemeprefs = getSharedPreferences(
                "theme", Context.MODE_PRIVATE);
        if (mThemeprefs!=null) {
            Integer sTheme =(int)(long) mThemeprefs.getLong("myTheme", R.style.AppTheme);
            setTheme(sTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container_history);
        if (fragment == null) {
            fragment = new HistoryFragment();
            fm.beginTransaction().add(R.id.container_history, fragment).commit();
        }
    }
}
