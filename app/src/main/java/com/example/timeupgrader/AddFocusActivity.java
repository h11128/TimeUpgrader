package com.example.timeupgrader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddFocusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_focus);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container_add_focus);
        if (fragment == null) {
            fragment = new AddFocusFragment();
            fm.beginTransaction().add(R.id.container_add_focus, fragment).commit();
        }
    }
}
