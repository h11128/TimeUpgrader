package com.example.timeupgrader;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.support.constraint.Constraints.TAG;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private Account mAccount;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private Button mIconButton;
    private Button mNickNameButton;
    private Button mChangePasswordButton;

    private Context mContext;
    private EditText mInput;
    boolean dialogOn;
    View view;
    public AccountFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "in AccountFragment onCreatView() called!!!");

        dialogOn = false;
        view = inflater.inflate(R.layout.fragment_account, container, false);



        mIconButton = view.findViewById(R.id.btnIcon);
        mIconButton.setOnClickListener(this);
        Log.i(TAG, "getFirstButton!!!");
        mNickNameButton = view.findViewById(R.id.btnNickName);

        mNickNameButton.setOnClickListener(this);
        Log.i(TAG, "getSecondButton!!!");
        mChangePasswordButton = view.findViewById(R.id.btnChangePassword);
        mChangePasswordButton.setOnClickListener(this);
        Log.i(TAG, "get all the buttons!!!");
        onClick(view);


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate() called!!!");

    }
    public void onClick(View view){
        Log.i(TAG, "in AccountFragment onClick() called!!!");
        switch(view.getId()){
            case R.id.btnIcon:
                Log.i(TAG, "in AccountFragment btnIcon pressed!!");
                break;
            case R.id.btnChangePassword:

                Log.i(TAG, "in AccountFragment btnChangePassword pressed!!");
                mFirebaseAuth = FirebaseAuth.getInstance();
                mFirebaseAuth.sendPasswordResetEmail(mAccount.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "An email has been sent to you.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Some error happens!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            case R.id.btnNickName:
                Log.i(TAG, "in AccountFragment btnNickName pressed!!");
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mUser != null) {
                    String oldname = mUser.getDisplayName();
                    String newUserName = "Nickname: " + oldname;
                    mNickNameButton.setText(newUserName);
                    changenameDialog();
                }
                else {
                    Toast.makeText(getActivity(), "You are not sign in yet!!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void changenameDialog() {
        dialogOn = true;


        AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
        mInput = new EditText(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Change Nickname");
        builder.setView(mInput);
        //mInput = (EditText) view.findViewById(R.id.ChangeName);
        //view.findViewById(R.id.ChangeName)
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "1!!");
                        dialog.dismiss();
                        Log.i(TAG, "2!!");

                        String mNewUserName = mInput.getText().toString();
                        Log.i(TAG, mNewUserName);
                        Log.i(TAG, "3!!");
                        String newUserName = "Nickname: " + mNewUserName;
                        Log.i(TAG, "4!!");
                        mNickNameButton.setText(newUserName);
                        Log.i(TAG, "5!!");
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(mNewUserName).build();
                        mUser.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.i(TAG, "User profile updated.");
                                        }
                                        else{
                                            Log.i(TAG, "Some error happens");
                                        }
                                    }
                                });
                        Log.i(TAG, "6!!");
                        dialogOn = false;

                    }
                });
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialogOn = false;
            }
        });

        builder.show();


    }
}

