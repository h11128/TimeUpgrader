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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private Button mIconButton;
    private Button mNickNameButton;
    private Button mChangePasswordButton;
    private FirebaseUser user;
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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential("user@example.com", "password1234");
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Password updated");
                                            } else {
                                                Log.d(TAG, "Error password not updated")
                                            }
                                        }
                                    });
                                } else {
                                    Log.d(TAG, "Error auth failed")
                                }
                            }
                        });
                break;
            case R.id.btnNickName:
                startActivity(new Intent(getActivity(), AchievementActivity.class));
                break;

        }
    }
}
