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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class HistoryFragment extends Fragment {
    private FirebaseUser mFUser;
    private User mUser;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    private List<SingleAct> mData;
    private HistoryAdapter adapter;

    public HistoryFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        mFUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser = User.getCurrentUser();
        mData = new ArrayList<>();
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

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("userAct")
            .child(mUser.getEmail().replace('.', ','));
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("status").getValue(Integer.class) == SingleAct.END) {
                        mData.add(new SingleAct(snapshot.child("id").getValue().toString(),
                                snapshot.child("name").getValue().toString(),
                                snapshot.child("description").getValue().toString(),
                                snapshot.child("type").getValue(Integer.class),
                                (long) snapshot.child("startTime").getValue(),
                                (boolean) snapshot.child("notify").getValue(),
                                (boolean) snapshot.child("isTiming").getValue(),
                                (long) snapshot.child("rewardPoint").getValue(),
                                snapshot.child("owner").getValue().toString(),
                                snapshot.child("status").getValue(Integer.class),
                                (long) snapshot.child("duration").getValue(),
                                (long) snapshot.child("currentTime").getValue(),
                                (boolean) snapshot.child("synced").getValue()));
                        Collections.sort(mData, new Comparator<SingleAct>() {
                            public int compare(SingleAct o1, SingleAct o2) {
                                return Long.compare(o2.getStartTime(), o1.getStartTime());
                            }
                        });
                        adapter = new HistoryAdapter(mData, getContext());
                        mRecyclerView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Firebase error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Log.i(TAG, "in history finish 3!!!");

        mRecyclerView = v.findViewById(R.id.historyRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.i(TAG, "in history finish 4!!!");

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

