package com.example.timeupgrader;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.util.Log;
import com.example.timeupgrader.UserAccountSchema.UASchema;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String RECEIVER_ACTION_FINISH = "receiver_action_finish";
    TaskDatabaseHelper dbHelper;

    Toolbar toolbar;
    ProgressBar progressBar;
    EditText userEmail;
    EditText userPassword;
    Button userLogin;
    FirebaseAuth firebaseAuth;
    private LoginActivity.FinishActivityReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG, "onCreate() called!!!");
        dbHelper = new TaskDatabaseHelper(this.getApplicationContext());
        mReceiver = new FinishActivityReceiver();
        registerFinishReceiver();

        toolbar = findViewById(R.id.toolbarLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        userEmail = findViewById(R.id.etUserEmail);
        userPassword = findViewById(R.id.etUserPassword);
        userLogin = findViewById(R.id.btnUserLogin);

        toolbar.setTitle("Login");

        firebaseAuth = FirebaseAuth.getInstance();
        userLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String em = userEmail.getText().toString();
                final String pw = userPassword.getText().toString();
                if (!em.equals("") && !pw.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(em, pw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        InsertData();
                                        LogData();
                                        UpdateData();
                                        LogData();
                                        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("email", em);
                                        editor.putString("password", pw);
                                        editor.apply();
                                        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                                    } else {
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else {
                    Log.i(TAG, "invalid username or password");
                    Toast.makeText(getApplicationContext(), "Empty e-mail or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void InsertData() {
        Log.i(TAG, "Inserting!!!");
        Account mAccount = new Account(userEmail.getText().toString(),userEmail.getText().toString(),
                userEmail.getText().toString(),userPassword.getText().toString());
        dbHelper.insert_UserAccount(mAccount);
    }
    public void UpdateData() {
        Log.i(TAG, "updating!!!");
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = UASchema.Column_UserName + " = ?";

        String[] selectionArgs = { userEmail.getText().toString() };
        ContentValues values = new ContentValues();
        values.put(UASchema.Column_UserName, "asdasd");
        int count = db.update(
                UASchema.Table_UserAccount,
                values,
                selection,
                selectionArgs);
    }
    public void LogData(){
        Log.i(TAG, "Logging!!!");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                UASchema.Column_UserId,
                UASchema.Column_UserName,
                UASchema.Column_Password,
                UASchema.Column_Email,
                UASchema.Column_UserId
        };
        Cursor cursor = db.query(
                UASchema.Table_UserAccount,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        while(cursor.moveToNext()) {
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UASchema.Column_Password));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UASchema.Column_UserName));
            String row = password + " " + username;
            Log.i(TAG, row);
        }

        cursor.close();
    }

    public void DeleteData(){
        Log.i(TAG, "Deleting!!!");
        Account mAccount = new Account("1",userEmail.getText().toString(),
                userEmail.getText().toString(),userPassword.getText().toString());
        dbHelper.delete_UserAccount(mAccount);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() called!!!");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() called!!!");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() called!!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() called!!!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() called!!!");

    }

    @Override
    protected void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        //DeleteData();
        dbHelper.close();
        super.onDestroy();
        Log.i(TAG, "onDestroy() called!!!");
    }

    private class FinishActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (RECEIVER_ACTION_FINISH.equals(intent.getAction())){
                LoginActivity.this.finish();
            }
        }
    }

    private void registerFinishReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION_FINISH);
        registerReceiver(mReceiver, intentFilter);
    }
}
