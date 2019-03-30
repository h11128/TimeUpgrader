package com.example.timeupgrader;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences mThemeprefs = getSharedPreferences(
                "theme", Context.MODE_PRIVATE);
        if (mThemeprefs!=null) {
            Integer sTheme =(int)(long) mThemeprefs.getLong("myTheme", R.style.AppTheme);
            setTheme(sTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container_setting);
        if (fragment == null) {
            fragment = new SettingFragment();
            fm.beginTransaction().add(R.id.container_setting, fragment).commit();
        }
    }
}
