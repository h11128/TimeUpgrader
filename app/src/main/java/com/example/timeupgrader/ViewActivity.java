package com.example.timeupgrader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewActivity extends AppCompatActivity {

    EditText editTextName;
    Button buttonAddTask;
    Button buttonPickTime;
    Spinner spinnerType;
    DatabaseReference databaseTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        buttonPickTime = (Button) findViewById(R.id.buttonPickTime);
        databaseTasks = FirebaseDatabase.getInstance().getReference();
        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog(){

    }

    private void addTask(){
        String name = editTextName.getText().toString();
        String type = spinnerType.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){

        }else {
            Toast.makeText(this,"You should enter a task name", Toast.LENGTH_LONG).show();
        }
    }
}
