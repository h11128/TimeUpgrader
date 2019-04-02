package com.example.timeupgrader;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

public class SettingFragment extends PreferenceFragmentCompat {

    public SettingFragment() {}

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName("settings");
        PreferenceManager.setDefaultValues(getContext(), R.xml.fragment_setting, false);
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
