package com.example.timeupgrader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MoreFragment extends Fragment implements View.OnClickListener {

    public MoreFragment() {}
    private Button mAccountButton;
    private Button mSettingButton;
    private Button mAchievementButton;
    private Button mThemeButton;
    private Button mHelpButton;
    private Button mSyncFrom;
    private Button mSyncTo;
    private PopupMenu mColorMenu;
    SharedPreferences mThemeprefs;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_more, container, false);
        mAccountButton = (Button) view.findViewById(R.id.btnAccountManagement);
        mAccountButton.setOnClickListener(this);

        mSettingButton = (Button) view.findViewById(R.id.btnSettings);
        mSettingButton.setOnClickListener(this);

        mAchievementButton = (Button) view.findViewById(R.id.btnAchievement);
        mAchievementButton.setOnClickListener(this);

        mThemeButton = (Button) view.findViewById(R.id.btnTheme);
        mThemeButton.setOnClickListener(this);

        mHelpButton = (Button) view.findViewById(R.id.btnHelp);
        mHelpButton.setOnClickListener(this);

        mSyncFrom = (Button) view.findViewById(R.id.btnSyncFrom);
        mSyncFrom.setOnClickListener(this);

        mSyncTo = (Button) view.findViewById(R.id.btnSyncTo);
        mSyncTo.setOnClickListener(this);

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
                mThemeprefs = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
                mColorMenu = new PopupMenu(getActivity(), view);
                mColorMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getActivity(), "Selected Item", Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.color_1:
                                mThemeprefs.edit().putLong("myTheme", R.style.AppTheme).apply();
                                ChangeColor();
                                return true;
                            case R.id.color_2:
                                mThemeprefs.edit().putLong("myTheme", R.style.AppTheme2).apply();
                                ChangeColor();
                                return true;
                            case R.id.color_3:
                                mThemeprefs.edit().putLong("myTheme", R.style.AppTheme3).apply();
                                ChangeColor();
                                return true;
                            case R.id.color_4:
                                mThemeprefs.edit().putLong("myTheme", R.style.AppTheme4).apply();
                                ChangeColor();
                                return true;
                            case R.id.color_5:
                                mThemeprefs.edit().putLong("myTheme", R.style.AppTheme5).apply();
                                ChangeColor();
                                return true;
                            case R.id.color_6:
                                mThemeprefs.edit().putLong("myTheme", R.style.AppTheme6).apply();
                                ChangeColor();
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
            case R.id.btnSyncFrom:
                if (ConnectionUtils.isConn(getContext())) {
                    syncFrom();
                }
                else {
                    Toast.makeText(getContext(), "No network connection.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnSyncTo:
                if (ConnectionUtils.isConn(getContext())) {
                    syncTo();
                }
                else {
                    Toast.makeText(getContext(), "No network connection.", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private void ChangeColor(){
        TaskStackBuilder.create(getActivity())
                .addNextIntent(new Intent(getActivity(), MainActivity.class))
                .startActivities();
        getActivity().finish();
    }

    private void syncFrom() {
        final TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(getContext());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("userAct").child(Email.getCurrentEmail().getEmail().replace('.', ','));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("status").getValue(Integer.class) < SingleAct.DELETE) {
                        Cursor cursor = dbHelper.getActCursorById(snapshot.child("id").getValue().toString());
                        if (cursor.getCount() == 0) {
                            dbHelper.insert_Activity(new SingleAct(snapshot.child("id").getValue().toString(),
                                    snapshot.child("name").getValue().toString(),
                                    snapshot.child("description").getValue().toString(),
                                    snapshot.child("type").getValue(Integer.class),
                                    (long) snapshot.child("startTime").getValue(),
                                    (boolean) snapshot.child("notify").getValue(),
                                    (boolean) snapshot.child("timing").getValue(),
                                    (long) snapshot.child("rewardPoint").getValue(),
                                    snapshot.child("owner").getValue().toString(),
                                    snapshot.child("status").getValue(Integer.class),
                                    (long) snapshot.child("duration").getValue(),
                                    (long) snapshot.child("currentTime").getValue(),
                                    (boolean) snapshot.child("synced").getValue(),
                            (String) snapshot.child("location").getValue()));
                        }
                        cursor.close();
                    }
                }
                Toast.makeText(getContext(), "Synced successfully!", Toast.LENGTH_LONG).show();
                Intent intentRestart = getActivity().getIntent();
                startActivity(intentRestart);
                getActivity().finish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Firebase error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void syncTo() {
        try {
            TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(getContext());
            FireBaseHelper fbHelper = new FireBaseHelper();
            int[] status = new int[]{SingleAct.SET, SingleAct.START, SingleAct.PAUSE, SingleAct.END};
            List<SingleAct> listAct = dbHelper.loadActivityByStatus(Email.getCurrentEmail().getEmail(), status, true);
            for (SingleAct act : listAct) {
                fbHelper.insertAct(act);
            }
            User u = User.getCurrentUser();
            if (u != null && u.getEmail().equals(Email.getCurrentEmail().getEmail())) {
                fbHelper.insertUser(u);
            }
            else {
                throw new InconsistentException("User email and email inconsistent!");
            }
            Toast.makeText(getContext(), "Synced successfully!", Toast.LENGTH_LONG).show();
            Intent intentRestart = getActivity().getIntent();
            startActivity(intentRestart);
            getActivity().finish();
        }
        catch (InconsistentException e) {
            Toast.makeText(getContext(), "Sync failed. " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
