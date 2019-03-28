package com.example.timeupgrader;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class FireBaseHelper {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String uEmail, uId, uUsername;
    long uLevel = -1, uPoint = -1, uNumFocusesDone = -1, uTimeCreated = -1;
    User u;

    public void getLoginUser(String email) {
        String cleanEmail = email.replace('.', ',');
        final DatabaseReference user = mDatabase.child("users").child(cleanEmail);
        final DatabaseReference userEmail = user.child("email");
        final DatabaseReference userId = user.child("id");
        final DatabaseReference userLevel = user.child("level");
        final DatabaseReference userPoint = user.child("point");
        final DatabaseReference userNumFocusesDone = user.child("numFocusesDone");
        final DatabaseReference userUsername = user.child("username");
        final DatabaseReference userTimeCreated = user.child("timeCreated");
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
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
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
}
