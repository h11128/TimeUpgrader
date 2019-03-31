package com.example.timeupgrader;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class HistoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TaskDatabaseHelper dbHelper;
    private DatabaseReference mDatabase;
    private List<SingleAct> mData;
    private HistoryAdapter adapter;

    public HistoryFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        dbHelper = new TaskDatabaseHelper(getContext());
        mData = new ArrayList<>();
        mRecyclerView = v.findViewById(R.id.historyRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.i(TAG, "in history finish variable!!!");
        /*mActList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(getActivity(), "Place Your First Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getActivity(), "Place Your Second Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 2) {

                    Toast.makeText(getActivity(), "Place Your Third Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {

                    Toast.makeText(getActivity(), "Place Your Forth Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {

                    Toast.makeText(getActivity(), "Place Your Fifth Option Code", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        Log.i(TAG, "in history finish 2!!!");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final User u = User.getCurrentUser();
        Log.i("uEmail", u != null ? u.getEmail() : "");
        Log.i("curEmail", Email.getCurrentEmail().getEmail());
        if (ConnectionUtils.isConn(getContext())) {
            Query query;
            if (u != null && u.getEmail().equals(Email.getCurrentEmail().getEmail())) {
                query = mDatabase.child("userAct").child(u.getEmail().replace('.', ','));
            } else {
                query = mDatabase.child("userAct").child(Email.getCurrentEmail().getEmail().replace('.', ','));
            }
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mData.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.child("status").getValue(Integer.class) == SingleAct.END) {
                            mData.add(new SingleAct(snapshot.child("id").getValue().toString(),
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
                                    (boolean) snapshot.child("synced").getValue()));
                        }
                    }
                    Collections.sort(mData, new Comparator<SingleAct>() {
                        public int compare(SingleAct o1, SingleAct o2) {
                            return Long.compare(o2.getStartTime(), o1.getStartTime());
                        }
                    });
                    adapter = new HistoryAdapter(mData, getContext());
                    mRecyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Toast.makeText(getActivity(), "Firebase error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    mData = dbHelper.loadActivityByStatus(u != null ? u.getEmail() : Email.getCurrentEmail().getEmail(), new int[]{SingleAct.END});
                    Collections.sort(mData, new Comparator<SingleAct>() {
                        public int compare(SingleAct o1, SingleAct o2) {
                            return Long.compare(o2.getStartTime(), o1.getStartTime());
                        }
                    });
                    adapter = new HistoryAdapter(mData, getContext());
                    mRecyclerView.setAdapter(adapter);
                }
            });
        }
        else {
            mData = dbHelper.loadActivityByStatus(u != null ? u.getEmail() : Email.getCurrentEmail().getEmail(), new int[]{SingleAct.END});
            if (mData == null || mData.size() == 0) {
                Toast.makeText(getContext(), "No local data, please check your network connection, then sync your data in More.", Toast.LENGTH_LONG).show();
            }
            else {
                Collections.sort(mData, new Comparator<SingleAct>() {
                    public int compare(SingleAct o1, SingleAct o2) {
                        return Long.compare(o2.getStartTime(), o1.getStartTime());
                    }
                });
            }
            adapter = new HistoryAdapter(mData, getContext());
            mRecyclerView.setAdapter(adapter);
        }
        Log.i(TAG, "in history finish 3!!!");

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

