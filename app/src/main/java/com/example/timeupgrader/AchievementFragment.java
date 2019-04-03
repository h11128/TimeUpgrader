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
    private TextView mPoints;
    private TextView mNumFocus;
    private User mUser;
    public AchievementFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_achievement, container, false);
        mAchievement = view.findViewById(R.id.textLevel);
        mPoints = view.findViewById(R.id.textPoint);
        mNumFocus = view.findViewById(R.id.textNumFocus);
        mUser = User.getCurrentUser();
        String level = "Level: "+mUser.getLevel()+"\n";

        String point = "Point:" + mUser.getPoint() + "\n";
        String numFocus = "Num of Focus:" + mUser.getNumFocusesDone() + "\n";
        mAchievement.setText(level);
        mPoints.setText(point);
        mNumFocus.setText(numFocus);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
