package com.example.timeupgrader;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
// import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDateTime;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ViewActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    Toolbar toolbar;
    EditText editTextName;
    EditText editTextDescription;
    Button buttonAddTask;
    Button buttonPickTime;
    Button buttonPickDate;
    // Button buttonPickLength;
    // Spinner spinnerType;
    TextView textView;
    DatabaseReference databaseTasks;
    TaskDatabaseHelper dbHelper;
    FireBaseHelper fbHelper;
    Date date;
    long startTime;
    int chosenDay, chosenYear, chosenMonth, chosenHour, chosenMinute;
    private LocalDateTime mLocalDateTime = new LocalDateTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences mThemeprefs = getSharedPreferences(
                "theme", Context.MODE_PRIVATE);
        if (mThemeprefs != null) {
            Integer sTheme = (int) (long) mThemeprefs.getLong("myTheme", R.style.AppTheme);
            setTheme(sTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        dbHelper = new TaskDatabaseHelper(getApplicationContext());
        fbHelper = new FireBaseHelper();

        toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("Add a new activity");
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTask);

        buttonPickTime = (Button) findViewById(R.id.buttonPickTime);
        buttonPickDate = (Button) findViewById(R.id.buttonPickDate);
        //buttonPickLength = (Button) findViewById(R.id.buttonPickLength);
        textView = (TextView) findViewById(R.id.textView);
        textView = (TextView) findViewById(R.id.textView2);
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
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, chosenYear);
                c.set(Calendar.MONTH, chosenMonth);
                c.set(Calendar.DAY_OF_MONTH, chosenDay);
                c.set(Calendar.HOUR_OF_DAY, chosenHour);
                c.set(Calendar.MINUTE, chosenMinute);
                c.set(Calendar.SECOND, 0);
                date = c.getTime();
                startTime = date.getTime();
                Date curDate = new Date();
                if (editTextName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a name!", Toast.LENGTH_LONG).show();
                }
                else if (startTime <= curDate.getTime()) {
                    Toast.makeText(getApplicationContext(), "Please set a future time!", Toast.LENGTH_LONG).show();
                }
                else {
                    UUID uuid = UUID.randomUUID();
                    Date currentTime = Calendar.getInstance().getTime();
                    long CurrentTime = currentTime.getTime();
                    // int rewardPoint = parseInt(spinnerType.getSelectedItem().toString());
                    SingleAct act = new SingleAct(uuid.toString(), editTextName.getText().toString(),
                            editTextDescription.getText().toString(), 0, startTime, true,
                            false, 66, Email.getCurrentEmail().getEmail(), SingleAct.SET, 0,
                            CurrentTime, false);
                    fbHelper.insertAct(act);
                    dbHelper.insert_Activity(act);
                    setAlarm(uuid.toString(), editTextName.getText().toString(), startTime);
                    Intent intent = new Intent(ViewActivity.this, MainFragment.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        chosenHour = hourOfDay;
        chosenMinute = minute;
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText((hourOfDay < 10 ? "0" : "") + hourOfDay + " : " + (minute < 10 ? "0" : "") + minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        chosenYear = year;
        chosenMonth = month;
        chosenDay = dayOfMonth;
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(currentDateString);
    }

//    private void addTask(){
//        String name = editTextName.getText().toString();
//        //String type = spinnerType.getSelectedItem().toString();
//        User u = User.getCurrentUser();
//        UUID uuid = UUID.randomUUID();
//        SingleAct act = new SingleAct(uuid.toString(), u.getUsername(), editTextName.toString(), 0，)；
//        fbHelper.insertAct(act);
//        taskDatabaseHelper.insert_Activity(act);
//        if(!TextUtils.isEmpty(name)){
//
//        }else {
//            makeText(this,"You should enter a task name", Toast.LENGTH_LONG).show();
//        }
//    }

    private void setAlarm(String id, String name, long start) {
        SharedPreferences sp = getSharedPreferences("alarm", Context.MODE_PRIVATE);
        int alarmCount = sp.getInt("count", 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction("com.example.ALARM_ACTION");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("alarmCount", alarmCount + 1);
        intent.putExtra("actName", name);
        intent.putExtra("actId", id);
        Log.i("alarmCount", alarmCount + "");
        Log.i("name", name);
        Log.i("id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmCount + 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, start, pendingIntent);
        }
        else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, start, pendingIntent);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("count", alarmCount + 1);
        editor.apply();
    }
}
