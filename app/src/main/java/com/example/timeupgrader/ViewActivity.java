package com.example.timeupgrader;

import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDateTime;

import java.util.Date;

public class ViewActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    EditText editTextName;
    Button buttonAddTask;
    Button buttonPickTime;
    Spinner spinnerType;
    TextView textView;
    DatabaseReference databaseTasks;
    private LocalDateTime mLocalDateTime = new LocalDateTime();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        buttonPickTime = (Button) findViewById(R.id.buttonPickTime);
        textView = (TextView)findViewById(R.id.textView);
        databaseTasks = FirebaseDatabase.getInstance().getReference();
        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker ");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("Hour: " + hourOfDay + " Minute " + minute);


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
