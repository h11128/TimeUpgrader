package com.example.timeupgrader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
                //
                break;
            case R.id.btnHelp:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
            case R.id.btnSync:
                //
                break;

        }
    }
    private void changeColorDialog() {



        AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
        mInput = new EditText(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Change Nickname");
        builder.setView(mInput);
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {



            }
        });
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.show();


    }
}
