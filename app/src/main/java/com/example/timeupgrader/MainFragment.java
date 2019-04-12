package com.example.timeupgrader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
// import android.widget.Toast;

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

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;
import static android.support.v4.content.ContextCompat.getSystemService;

import android.Manifest;

public class MainFragment extends Fragment {

    private final static int VIEW_ACTIVITY_REQUEST_CODE = 1;
    public static final String RECEIVER_ACTION_DATA_CHANGE = "receiver_action_data_change";

    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private MainAdapter adapter;
    private List<SingleAct> mData;
    private TaskDatabaseHelper dbHelper;
    private DatabaseReference mDatabase;

    private AdapterDataChangeReceiver mReceiver;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "in Main onCreateView called!!!");
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mReceiver = new AdapterDataChangeReceiver();
        registerDataChangeReceiver();

        dbHelper = new TaskDatabaseHelper(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mData = new ArrayList<>();
        mRecyclerView = v.findViewById(R.id.mainRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        final User u = User.getCurrentUser();
        Log.i("uEmail", u != null ? u.getEmail() : "");
        Log.i("curEmail", Email.getCurrentEmail().getEmail());
        Log.i(TAG, "in Main begin query database!!!");
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
                        if (snapshot.child("status").getValue(Integer.class) < SingleAct.PAUSE) {
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
                            return Long.compare(o1.getStartTime(), o2.getStartTime());
                        }
                    });
                    adapter = new MainAdapter(mData, getContext());
                    mRecyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Toast.makeText(getContext(), "Firebase error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    mData = dbHelper.loadActivityByStatus(u != null ? u.getEmail() : Email.getCurrentEmail().getEmail(), new int[]{SingleAct.SET, SingleAct.START}, false);
                    Collections.sort(mData, new Comparator<SingleAct>() {
                        public int compare(SingleAct o1, SingleAct o2) {
                            return Long.compare(o1.getStartTime(), o2.getStartTime());
                        }
                    });
                    adapter = new MainAdapter(mData, getContext());
                    mRecyclerView.setAdapter(adapter);
                }
            });
        }
        else {
            mData = dbHelper.loadActivityByStatus(u != null ? u.getEmail() : Email.getCurrentEmail().getEmail(), new int[]{SingleAct.SET, SingleAct.START}, false);
            if (mData == null || mData.size() == 0) {
                showDataDialog();
            }
            else {
                Collections.sort(mData, new Comparator<SingleAct>() {
                    public int compare(SingleAct o1, SingleAct o2) {
                        return Long.compare(o1.getStartTime(), o2.getStartTime());
                    }
                });
            }
            adapter = new MainAdapter(mData, getContext());
            mRecyclerView.setAdapter(adapter);
        }
        Log.i(TAG, "in Main finish query database!!!");
        /*adapter = new MainAdapter(mData);
        mRecyclerView.setAdapter(adapter);*/

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "in Main onActivityCreated called!!!");

        fab = getActivity().findViewById(R.id.addactivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), ViewActivity.class), VIEW_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case VIEW_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //adapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
    }

    private void showDataDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("No local data, please check your network connection, then go to More and sync your data from cloud database.");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }

    public class AdapterDataChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (adapter != null) {
                String id = intent.getStringExtra("actId");
                int status = intent.getIntExtra("status", -1);
                if (status != -1 && !id.equals("")) {
                    for (int i = 0; i < mData.size(); i++) {
                        if (mData.get(i).getId().equals(id)) {
                            mData.get(i).setStatus(status);
                        }
                    }
                    adapter = new MainAdapter(mData, getContext());
                    mRecyclerView.setAdapter(adapter);
                }
            }
        }
    }

    private void registerDataChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION_DATA_CHANGE);
        getActivity().registerReceiver(mReceiver, intentFilter);
    }
}
