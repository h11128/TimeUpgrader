package com.example.timeupgrader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.support.constraint.Constraints.TAG;

public class GroupFragment extends Fragment {
    private View view;
    private FloatingActionButton mFab;
    private boolean dialogOn;
    public GroupFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group, container, false);
        /*mFab = getActivity().findViewById(R.id.addmember);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewActivity.class));
            }
        });*/
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addMemberDialog() {
        dialogOn = true;


        final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
        final EditText mInput = new EditText(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Change Nickname");
        builder.setView(mInput);
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                String mMemberEmail = mInput.getText().toString();
                Log.i(TAG, "member email is"+mMemberEmail);



                //TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(getActivity().getApplicationContext());
                //dbHelper.updateUsername(mUser,mNewUserName);
                /*Log.i(TAG, "On account 1");
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                Log.i(TAG, "On account 2");
                mDatabase.child("users").child(mMemberEmail.replace('.', ',')).child("username").setValue(mNewUserName)
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
                dialogOn = false;
                Log.i(TAG, "On account 5");*/

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
