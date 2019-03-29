package com.example.timeupgrader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.support.constraint.Constraints.TAG;

public class MoreFragment extends Fragment implements View.OnClickListener {

    public MoreFragment() {}
    private Button mAccountButton;
    private Button mSettingButton;
    private Button mAchievementButton;
    private Button mThemeButton;
    private Button mHelpButton;
    private Button mSyncButton;
    private PopupMenu mColorMenu;
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_more, container, false);
        mAccountButton =(Button) view.findViewById(R.id.btnAccountManagement);
        mAccountButton.setOnClickListener(this);

        mSettingButton =(Button) view.findViewById(R.id.btnSettings);
        mSettingButton.setOnClickListener(this);

        mAchievementButton =(Button) view.findViewById(R.id.btnAchievement);
        mAchievementButton.setOnClickListener(this);

        mThemeButton =(Button) view.findViewById(R.id.btnTheme);
        mThemeButton.setOnClickListener(this);

        mHelpButton =(Button) view.findViewById(R.id.btnHelp);
        mHelpButton.setOnClickListener(this);

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

        switch(view.getId()){
            case R.id.btnAccountManagement:
                startActivity(new Intent(getActivity(), AccountActivity.class));
                break;
            case R.id.btnSettings:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.btnAchievement:
                startActivity(new Intent(getActivity(), AchievementActivity.class));
                break;
            case R.id.btnTheme:
                mColorMenu = new PopupMenu(getActivity(), view);
                mColorMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getActivity(), "Selected Item: ", Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.color_1:
                                ChangeColor(R.id.color_1);
                                return true;
                            case R.id.color_2:
                                // do your code
                                return true;
                            case R.id.color_3:
                                // do your code
                                return true;
                            case R.id.color_4:
                                // do your code
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                mColorMenu.inflate(R.menu.menu_changecolor);
                mColorMenu.show();
                break;

            case R.id.btnHelp:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
            case R.id.btnSync:
                //
                break;

        }
    }

    public void ChangeColor(Integer id){
        ConstraintLayout li=view.findViewById(R.id.splashActivity);
        li.setBackgroundColor(id);
    }



}
