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
import android.widget.TextView;
import android.widget.Toast;

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
    private Account mAccount;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private TextView mUserName;
    private String mNewUserName;
    View view;
    public AccountFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        mUserName = (TextView) view.findViewById(R.id.oldUserName);

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
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserName.setText(mUser.getDisplayName());

        onClick(view);
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnIcon:

                break;
            case R.id.btnChangePassword:
                mAccount = Account.getCurrentAccount();

                mFirebaseAuth = FirebaseAuth.getInstance();

                mFirebaseAuth.sendPasswordResetEmail(mAccount.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "An email has been sent to you.", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            case R.id.btnNickName:

                if (mUser != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                    final EditText input = (EditText) view.findViewById(R.id.input);

                    builder.setView(view);

                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mNewUserName = input.getText().toString();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                    mUserName.setText(mNewUserName);
                    mAccount.setUsername(mNewUserName);
                } else {
                    Toast.makeText(getActivity(), "You are not sign in yet!!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
