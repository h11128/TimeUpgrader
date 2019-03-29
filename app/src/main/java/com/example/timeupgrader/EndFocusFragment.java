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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EndFocusFragment extends Fragment {

    TaskDatabaseHelper dbHelper;
    private DatabaseReference mDatabase;

    TextView completedName;
    TextView points;
    Button btnReturn;

    public EndFocusFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_end_focus, container, false);

        completedName = v.findViewById(R.id.fCompletedTask);
        points = v.findViewById(R.id.fPoints);
        btnReturn = v.findViewById(R.id.btnReturnAfterFocus);

        dbHelper = new TaskDatabaseHelper(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getActivity().getIntent();
        long pts = convertToPoints(intent.getLongExtra("time", 0));
        completedName.setText(intent.getStringExtra("name"));
        points.setText(pts + " pts");
        User u = User.getCurrentUser();
        Log.i("pts", u.getPoint() + "");
        Log.i("num", u.getNumFocusesDone() + "");
        u.addPoint(pts);
        u.setNumFocusesDone(u.getNumFocusesDone() + 1);
        Log.i("pts", u.getPoint() + "");
        Log.i("num", u.getNumFocusesDone() + "");
        dbHelper.updatePoint(u, u.getPoint());
        dbHelper.updateNumFocus(u, u.getNumFocusesDone());
        DatabaseReference dbr = mDatabase.child("users").child(u.getEmail().replace('.', ','));
        dbr.child("point").setValue(u.getPoint());
        dbr.child("numFocusesDone").setValue(u.getNumFocusesDone());

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private long convertToPoints(long time) {
        return time / 10000;
    }
}
