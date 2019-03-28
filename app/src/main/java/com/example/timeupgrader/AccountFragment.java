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

import static android.support.constraint.Constraints.TAG;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private Account mAccount;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private Button mIconButton;
    private Button mNickNameButton;
    private Button mChangePasswordButton;
    private String mNewUserName;
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
        mAccount = Account.getCurrentAccount();
        mContext = getContext();
        Log.i(TAG, "getcontext!!!");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_changename);
        builder.setTitle("Change Nickname");
        mInput = view.findViewById(R.id.ChangeName);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "1!!");
                        dialog.dismiss();
                        Log.i(TAG, "2!!");

                        mNewUserName = mInput.getText().toString();
                        Log.i(TAG, mNewUserName);
                        Log.i(TAG, "3!!");
                        String newUserName = "Nickname: " + mAccount.getUsername();
                        Log.i(TAG, "4!!");
                        mNickNameButton.setText(newUserName);
                        Log.i(TAG, "5!!");
                        mAccount.setUsername(newUserName);
                        Log.i(TAG, "6!!");
                        dialogOn = false;

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialogOn = false;
            }
        });

        builder.create().show();


    }
}

