package com.example.timeupgrader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AchievementFragment extends Fragment {
    private View view;
    private TextView mAchievement;
    private User mUser;
    public AchievementFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_achievement, container, false);
        mAchievement = view.findViewById(R.id.textAchievement);
        mUser = User.getCurrentUser();
        String display ="level: "+mUser.getLevel()+"\n"+"point:"+ mUser.getPoint()+"\n"
                +"NumFocus: "+mUser.getNumFocusesDone();
        mAchievement.setText(display);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
