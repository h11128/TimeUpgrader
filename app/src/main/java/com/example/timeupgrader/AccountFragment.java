package com.example.timeupgrader;
import android.content.DialogInterface;
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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.support.constraint.Constraints.TAG;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private User mUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFUser;
    private Button mIconButton;
    private Button mNickNameButton;
    private Button mChangePasswordButton;
    private EditText mInput;
    private String mButtonText;
    boolean dialogOn;
    View view;
    public AccountFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "in AccountFragment onCreatView() called!!!");

        dialogOn = false;
        view = inflater.inflate(R.layout.fragment_account, container, false);

        mUser = User.getCurrentUser();

        mIconButton = view.findViewById(R.id.btnIcon);
        mIconButton.setOnClickListener(this);
        Log.i(TAG, "getFirstButton!!!");

        mNickNameButton = view.findViewById(R.id.btnNickName);
        mNickNameButton.setOnClickListener(this);

        Log.i(TAG, "getSecondButton!!!");
        mChangePasswordButton = view.findViewById(R.id.btnChangePassword);
        mChangePasswordButton.setOnClickListener(this);
        Log.i(TAG, "get all the buttons!!!");

        mButtonText = "Nickname: " + mUser.getUsername();
        Log.i(TAG, "in AccountFragment changetext!!");
        mNickNameButton.setText(mButtonText);
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
                mFirebaseAuth.sendPasswordResetEmail(mUser.getEmail())
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

                mFUser = FirebaseAuth.getInstance().getCurrentUser();
                Log.i(TAG, "in AccountFragment btnNickName 3!!");
                if (mFUser != null) {
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
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        String mNewUserName = mInput.getText().toString();
                        Log.i(TAG, "your user name is"+mNewUserName);

                        mButtonText = "Nickname: " + mNewUserName;
                        mNickNameButton.setText(mButtonText);
                        Toast.makeText(getActivity(), "changed!!", Toast.LENGTH_SHORT).show();

                        TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(getActivity().getApplicationContext());
                        dbHelper.updateUsername(mUser, mNewUserName);
                        Log.i(TAG, "On account 1");
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("users").child(mUser.getEmail().replace('.', ',')).child("username").setValue(mNewUserName)
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
                        mUser.setUsername(mNewUserName);
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

