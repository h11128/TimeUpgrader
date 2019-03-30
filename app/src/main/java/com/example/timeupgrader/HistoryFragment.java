package com.example.timeupgrader;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class HistoryFragment extends Fragment {
    private View view;
    private FirebaseUser mFUser;
    private User mUser;
    private ListView mActList;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> ActName;
    private ArrayList<Long> ActDuration;
    private ArrayList<Integer> ActIcon;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);

        mFUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser = User.getCurrentUser();
        ActName = new ArrayList<>();
        ActDuration = new ArrayList<>();
        ActIcon = new ArrayList<>();
        Log.i(TAG, "in history finsh variable!!!");
        final ActListAdapter adapter = new ActListAdapter(getActivity(), ActName, ActDuration, ActIcon);
        Log.i(TAG, "in history 1");
        mActList = view.findViewById(R.id.ActList);
        mActList.setAdapter(adapter);
        mActList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });
        Log.i(TAG, "in history finsh 2!!!");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("userAct")
            .child(mUser.getEmail().replace('.', ','));
        mDatabaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null) {
                            UpdateList((Map<String, Object>) dataSnapshot.getValue());
                            adapter.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Some error happens", Toast.LENGTH_SHORT).show();
                        //handle databaseError
                    }
                });
        Log.i(TAG, "in history finsh 3!!!");
        adapter.notifyDataSetChanged();
        Log.i(TAG, "in history finsh 4!!!");




        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void UpdateList(Map<String,Object> userAct) {

        for (Map.Entry<String, Object> entry : userAct.entrySet()) {
            Map singleActivity = (Map) entry.getValue();
            if ((Integer) singleActivity.get("status") != 3) {

                ActName.add((String) singleActivity.get("name"));
                ActDuration.add((Long) singleActivity.get("duration"));
                ActIcon.add((Integer) R.drawable.baseline_account_circle_black_24);
            }
        }


    }

    public class ActListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final ArrayList<String> ActName;
        private final ArrayList<Long> ActDuration;
        private final ArrayList<Integer> ActIcon;

        public ActListAdapter(Activity context, ArrayList<String> ActName,ArrayList<Long> ActDuration
                , ArrayList<Integer> ActIcon) {
            super(context, R.layout.list_activity, ActName);
            this.context=context;
            this.ActName=ActName;
            this.ActDuration=ActDuration;
            this.ActIcon=ActIcon;
            Log.i(TAG, "in history finsh initialize!!!");
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.list_activity, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.ActName);
            TextView subtitleText = (TextView) rowView.findViewById(R.id.ActDuration);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.ActIcon);

            for(int i = 0; i < ActName.size(); i++)
            {
                titleText.setText(ActName.get(i));
                subtitleText.setText(String.format("%d", ActDuration.get(i)));
                imageView.setImageResource(ActIcon.get(i));
            }
            Log.i(TAG, "in history finsh getview!!!");
            return rowView;

        }
    }
}

