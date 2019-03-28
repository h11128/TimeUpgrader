package com.example.timeupgrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.support.constraint.Constraints.TAG;

public class MoreFragment extends Fragment implements View.OnClickListener {

    public MoreFragment() {}
    private Button mAccountButton;
    private Button mSettingButton;
    private Button mAchievementButton;
    private Button mThemeButton;
    private Button mHelpButton;
    private Button mSyncButton;
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "1!!");
        view =  inflater.inflate(R.layout.fragment_more, container, false);
        mAccountButton =(Button) view.findViewById(R.id.btnAccountManagement);
        mAccountButton.setOnClickListener(this);
        Log.i(TAG, "2!!");
        mSettingButton =(Button) view.findViewById(R.id.btnSettings);
        mSettingButton.setOnClickListener(this);
        Log.i(TAG, "3!!");
        mAchievementButton =(Button) view.findViewById(R.id.btnAchievement);
        mAchievementButton.setOnClickListener(this);
        Log.i(TAG, "4!!");
        mThemeButton =(Button) view.findViewById(R.id.btnTheme);
        mThemeButton.setOnClickListener(this);
        Log.i(TAG, "5!!");
        mHelpButton =(Button) view.findViewById(R.id.btnHelp);
        mHelpButton.setOnClickListener(this);
        Log.i(TAG, "6!!");
        mSyncButton =(Button) view.findViewById(R.id.btnSync);
        mSyncButton.setOnClickListener(this);
        onClick(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void onClick(View view){
        Log.i(TAG, "7!!");
        switch(view.getId()){
            case R.id.btnAccountManagement:
                Log.i(TAG, "8!!");
                startActivity(new Intent(getActivity(), AccountActivity.class));
                break;
            case R.id.btnSettings:
                Log.i(TAG, "9!!");
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.btnAchievement:
                Log.i(TAG, "10!!");
                startActivity(new Intent(getActivity(), AchievementActivity.class));
                break;
            case R.id.btnTheme:
                //
                break;
            case R.id.btnHelp:
                //
                break;
            case R.id.btnSync:
                //
                break;

        }
    }
}
