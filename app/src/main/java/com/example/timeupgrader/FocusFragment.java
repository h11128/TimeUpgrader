package com.example.timeupgrader;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FocusFragment extends Fragment {

    TextView name;
    TextView description;
    TextView remainingTime;
    FocusCircleTimer focusTimer;
    CountDownTimer cTimer;
    int hr;
    int min;
    int sec;
    long total;
    long progress = 0;

    public FocusFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_focus, container, false);

        name = v.findViewById(R.id.fName);
        description = v.findViewById(R.id.fDescription);
        remainingTime = v.findViewById(R.id.fRemainingTime);
        focusTimer = v.findViewById(R.id.focusTimer);

        Intent intent = getActivity().getIntent();
        name.setText(intent.getStringExtra("name"));
        description.setText(intent.getStringExtra("description"));
        hr = intent.getIntExtra("hr", 0);
        min = intent.getIntExtra("min", 0);
        sec = intent.getIntExtra("sec", 0);
        total = ((hr * 60 + min) * 60 + sec) * 1000;
        // remainingTime.setText(format((int)(total / 1000L)));
        if (cTimer == null) {
            cTimer = new CountDownTimer(total, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (getActivity() != null) {
                        int remainTime = (int) (millisUntilFinished / 1000L);
                        remainingTime.setText(format(remainTime));
                        long curProgress = (long)((total - millisUntilFinished) / (double)total * focusTimer.getMax());
                        if (curProgress > progress) {
                            progress = curProgress;
                            focusTimer.setProgress((int)progress);
                        }
                    }
                }
                @Override
                public void onFinish() {
                    remainingTime.setText(format(0));
                    focusTimer.setProgress(focusTimer.getMax());
                    getActivity().finish();
                }
            };
            cTimer.start();
        }

        return v;
    }

    private String format(int time) {
        int sec = time % 60;
        int min = (time / 60) % 60;
        int hr = (time / 60) / 60;
        String formatTime = "";
        if (hr < 10) formatTime += "0";
        formatTime += hr + ":";
        if (min < 10) formatTime += "0";
        formatTime += min + ":";
        if (sec < 10) formatTime += "0";
        formatTime += sec;
        return formatTime;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cTimer != null) {
            cTimer.cancel();
            cTimer = null;
        }
    }
}
