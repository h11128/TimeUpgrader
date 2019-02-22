package com.example.timeupgrader;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ProgressBar progressBar;
    EditText email;
    EditText password;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    Button signup;
    Button login;
    static MainActivity mainActivity;
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    String savedEmail;
    String savedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        Log.i(TAG, "onCreate() called!!!");

        firebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        savedEmail = sp.getString("email", null);
        savedPassword = sp.getString("password", null);
        if (savedEmail != null && !savedEmail.equals("") && savedPassword != null && !savedPassword.equals("")) {
            firebaseAuth.signInWithEmailAndPassword(savedEmail, savedPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            }else{
                                Toast.makeText(MainActivity.this, task.getException().getMessage()
                                        , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else {
            setContentView(R.layout.activity_main);

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
                    if (isEmail(email.getText().toString()) && isPassword(password.getText().toString())) {
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Registered successfully",
                                                    Toast.LENGTH_LONG).show();
                                            email.setText("");
                                            password.setText("");
                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        } else {
                                            Toast.makeText(MainActivity.this, task.getException().getMessage(),
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
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
    }

    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
}
