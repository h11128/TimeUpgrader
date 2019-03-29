package com.example.timeupgrader;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

public class HistoryFragment extends Fragment {
    private View view;
    private FirebaseUser mFUser;
    private User mUser;
    private ListView mActList;
    private DatabaseReference mDatabaseReference;
    String[] ActName = {
            "Title 1", "Title 2",
            "Title 3", "Title 4",
            "Title 5",
    };

    String[] ActDuration = {
            "Sub Title 1", "Sub Title 2",
            "Sub Title 3", "Sub Title 4",
            "Sub Title 5",
    };

    Integer[] ActIcon = {
            R.drawable.baseline_account_circle_black_24, R.drawable.baseline_account_circle_black_24,
            R.drawable.baseline_account_circle_black_24, R.drawable.baseline_account_circle_black_24,
            R.drawable.baseline_account_circle_black_24,
    };

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        mFUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser = User.getCurrentUser();


        ActListAdapter adapter = new ActListAdapter(getActivity(), ActName, ActDuration, ActIcon);

        mActList = view.findViewById(R.id.ActList);
        mActList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /*mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user")
            .child(mUser.getEmail()).child("userAct");
        mDatabaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        UpdateList((Map<String,Object>) dataSnapshot.getValue());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        */
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

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void UpdateList(Map<String,Object> userAct) {

        ArrayList<Long> singleAct = new ArrayList<>();


        for (Map.Entry<String, Object> entry : userAct.entrySet()){


            Map singleActivity = (Map) entry.getValue();


            singleActivity.get("ActName");
        }


    }

    public class ActListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] ActName;
        private final String[] ActDuration;
        private final Integer[] ActIcon;

        public ActListAdapter(Activity context, String[] ActName,String[] ActDuration, Integer[] ActIcon) {
            super(context, R.layout.list_activity, ActName);
            this.context=context;
            this.ActName=ActName;
            this.ActDuration=ActDuration;
            this.ActIcon=ActIcon;

        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.list_activity, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.ActName);
            TextView subtitleText = (TextView) rowView.findViewById(R.id.ActDuration);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.ActIcon);


            titleText.setText(ActName[position]);
            subtitleText.setText(ActDuration[position]);
            imageView.setImageResource(ActIcon[position]);

            return rowView;

        };
    }
}

