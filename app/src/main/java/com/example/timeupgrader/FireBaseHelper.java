package com.example.timeupgrader;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseHelper {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String uEmail, uId, uUsername;
    private long uLevel = -1, uPoint = -1, uNumFocusesDone = -1, uTimeCreated = -1;
    private User u;

    public void getLoginUser(String email) {
        String cleanEmail = email.replace('.', ',');
        DatabaseReference user = mDatabase.child("users").child(cleanEmail);
        /*final DatabaseReference userEmail = user.child("email");
        final DatabaseReference userId = user.child("id");
        final DatabaseReference userLevel = user.child("level");
        final DatabaseReference userPoint = user.child("point");
        final DatabaseReference userNumFocusesDone = user.child("numFocusesDone");
        final DatabaseReference userUsername = user.child("username");
        final DatabaseReference userTimeCreated = user.child("timeCreated");*/
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uEmail = dataSnapshot.child("email").getValue(String.class);
                uId = dataSnapshot.child("id").getValue(String.class);
                uLevel = (Long) dataSnapshot.child("level").getValue();
                uPoint = (Long) dataSnapshot.child("point").getValue();
                uNumFocusesDone = (Long) dataSnapshot.child("numFocusesDone").getValue();
                uUsername = dataSnapshot.child("username").getValue(String.class);
                uTimeCreated = (Long) dataSnapshot.child("timeCreated").getValue();
                u = new User(uId, uEmail, uUsername, uPoint, uLevel, uNumFocusesDone, new ArrayList(), uTimeCreated);
                Log.i("uEmail", u.getEmail());
                Log.i("uPoint", u.getPoint() + "");
                Log.i("uTimeCreated", u.getTimeCreated() + "");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Firebase error: ", databaseError.getMessage());
            }
        });
        /*userEmail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uEmail = dataSnapshot.getValue(String.class);
                Log.i("uEmail", uEmail);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("uEmail", "Cancelled");
            }
        });
        userId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uId = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        userLevel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uLevel = (Long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        userPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uPoint = (Long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        userNumFocusesDone.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uNumFocusesDone = (Long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        userUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uUsername = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        userTimeCreated.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uTimeCreated = (Long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });*/
        // return new User(uId, uEmail, uUsername, uPoint, uLevel, uNumFocusesDone, new ArrayList(), uTimeCreated);
    }

    public void insertUser(User user) {
        String cleanEmail = user.getEmail().replace('.', ',');
        mDatabase.child("users").child(cleanEmail).setValue(user);
    }

    public void insertAct(SingleAct act) {
        String cleanOwnerEmail = act.getOwner().replace('.', ',');
        mDatabase.child("userAct").child(cleanOwnerEmail).child(act.getId()).setValue(act);
        mDatabase.child("act").child(act.getId()).setValue(act);
    }

    public void updateActStatus(SingleAct act, int status) {
        mDatabase.child("act").child(act.getId()).child("status").setValue(status);
    }
}
