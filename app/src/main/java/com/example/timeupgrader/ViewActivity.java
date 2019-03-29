package com.example.timeupgrader;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDateTime;

import java.text.DateFormat;
import java.util.Calendar;

public class ViewActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText editTextName;
    Button buttonAddTask;
    Button buttonPickTime;
    Button buttonPickDate;
    Spinner spinnerType;
    TextView textView;
    DatabaseReference databaseTasks;
    FireBaseHelper fbHelper;
    TaskDatabaseHelper taskDatabaseHelper;
    private LocalDateTime mLocalDateTime = new LocalDateTime();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        buttonPickTime = (Button) findViewById(R.id.buttonPickTime);
        buttonPickDate = (Button) findViewById(R.id.buttonPickDate);
        textView = (TextView)findViewById(R.id.textView);
        textView = (TextView)findViewById(R.id.textView2);
        databaseTasks = FirebaseDatabase.getInstance().getReference();
        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker ");
            }
        });
        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(hourOfDay + "  :  " + minute );
//        User u = User.getCurrentUser();
//        SingleAct act = new SingleAct()；
//        fbHelper.insertAct(act);
//        taskDatabaseHelper.insert_Activity(act);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setText(currentDateString);
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
