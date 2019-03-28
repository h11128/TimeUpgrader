package com.example.timeupgrader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();
    public static final String RECEIVER_ACTION_FINISH = "receiver_action_finish";
    TaskDatabaseHelper dbHelper;
    private DatabaseReference mDatabase;
    private FireBaseHelper fbHelper = new FireBaseHelper();

    ProgressBar progressBar;
    EditText email;
    EditText password;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    Button signup;
    Button login;
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    String savedEmail;
    String savedPassword;
    String em;
    String pw;

    private FinishActivityReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate() called!!!");
        dbHelper = new TaskDatabaseHelper(this.getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mReceiver = new FinishActivityReceiver();
        registerFinishReceiver();

        firebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        savedEmail = sp.getString("email", null);
        savedPassword = sp.getString("password", null);
        if (savedEmail != null && !savedEmail.equals("") && savedPassword != null && !savedPassword.equals("")) {
            firebaseAuth.signInWithEmailAndPassword(savedEmail, savedPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fbHelper.getLoginUser(savedEmail);
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(SignupActivity.this, task.getException().getMessage()
                                        , Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                finish();
                            }
                        }
                    });
        }
        else {
            setContentView(R.layout.activity_signup);

            toolbar = findViewById(R.id.toolbarMain);
            progressBar = findViewById(R.id.progressBarMain);
            email = findViewById(R.id.etEmail);
            password = findViewById(R.id.etPassword);
            signup = findViewById(R.id.btnSignup);
            login = findViewById(R.id.btnLogin);

            toolbar.setTitle("Welcome to TimeUpgrader!");

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    em = email.getText().toString();
                    pw = password.getText().toString();
                    if (isEmail(em) && isPassword(pw)) {
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(em, pw)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            Date d = new Date();
                                            Log.i("Time Created", d.toString());
                                            Log.i("email", em);
                                            Log.i("password", pw);
                                            Account newAccount = new Account("", em, "", pw, d);
                                            User newUser = new User("", em,"", 0, 0, 0, new ArrayList(), d.getTime());
                                            dbHelper.insert_UserAccount(newAccount, newUser);
                                            String cleanEmail = em.replace('.', ',');
                                            mDatabase.child("users").child(cleanEmail).setValue(newUser);
                                            Toast.makeText(SignupActivity.this, "Signed up successfully",
                                                    Toast.LENGTH_LONG).show();
                                            email.setText("");
                                            password.setText("");
                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(SignupActivity.this, task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid E-mail or password", Toast.LENGTH_LONG).show();
                    }
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }
            });
        }
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
        super.onDestroy();
        Log.i(TAG, "onDestroy() called!!!");
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        dbHelper.close();
    }

    private class FinishActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (RECEIVER_ACTION_FINISH.equals(intent.getAction())){
                SignupActivity.this.finish();
            }
        }
    }

    private void registerFinishReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION_FINISH);
        registerReceiver(mReceiver, intentFilter);
    }

    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
}
