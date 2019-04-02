package com.example.timeupgrader;

// import android.util.Log;

import android.content.ContentValues;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

// import java.util.ArrayList;

public class FireBaseHelper {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String uEmail, uId, uUsername;
    private long uLevel = -1, uPoint = -1, uNumFocusesDone = -1, uTimeCreated = -1;
    private User u;

    /*public void getLoginUser(String email) {
        String cleanEmail = email.replace('.', ',');
        DatabaseReference user = mDatabase.child("users").child(cleanEmail);
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
    }*/

    public void insertUser(User user) {
        String cleanEmail = user.getEmail().replace('.', ',');
        mDatabase.child("users").child(cleanEmail).setValue(user);
    }

    public void updatePoint(User user, long point) {
        String cleanEmail = user.getEmail().replace('.', ',');
        mDatabase.child("users").child(cleanEmail).child("point").setValue(point);
    }

    public void updatePointByEmail(String email, long point) {
        String cleanEmail = email.replace('.', ',');
        mDatabase.child("users").child(cleanEmail).child("point").setValue(point);
    }

    public void insertAct(SingleAct act) {
        String cleanOwnerEmail = act.getOwner().replace('.', ',');
        mDatabase.child("userAct").child(cleanOwnerEmail).child(act.getId()).setValue(act);
        mDatabase.child("act").child(act.getId()).setValue(act);
    }

    public void updateActStatus(SingleAct act, int status) {
        String cleanOwnerEmail = act.getOwner().replace('.', ',');
        mDatabase.child("userAct").child(cleanOwnerEmail).child(act.getId()).child("status").setValue(status);
        mDatabase.child("act").child(act.getId()).child("status").setValue(status);
    }

    public void updateActStatusById(final String id, final int status) {
        Query query = mDatabase.child("act").child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cleanOwnerEmail = dataSnapshot.child("owner").getValue(String.class).replace('.', ',');
                mDatabase.child("userAct").child(cleanOwnerEmail).child(id).child("status").setValue(status);
                mDatabase.child("act").child(id).child("status").setValue(status);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Toast.makeText(getContext(), "Firebase error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
