package com.example.timeupgrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private Button mIconButton;
    private Button mNickNameButton;
    private Button mChangePasswordButton;
    View view;
    public AccountFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        mIconButton =(Button) view.findViewById(R.id.btnIcon);
        mIconButton.setOnClickListener(this);

        mNickNameButton =(Button) view.findViewById(R.id.btnChangePassword);
        mNickNameButton.setOnClickListener(this);

        mChangePasswordButton =(Button) view.findViewById(R.id.btnNickName);
        mChangePasswordButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnIcon:

                break;
            case R.id.btnChangePassword:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.btnNickName:
                startActivity(new Intent(getActivity(), AchievementActivity.class));
                break;

        }
    }
}
