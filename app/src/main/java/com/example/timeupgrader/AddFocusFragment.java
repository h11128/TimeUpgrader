package com.example.timeupgrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public class AddFocusFragment extends Fragment {

    NumberPicker hr;
    NumberPicker min;
    NumberPicker sec;

    public AddFocusFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_focus, container, false);

        hr = v.findViewById(R.id.focusHr);
        min = v.findViewById(R.id.focusMin);
        sec = v.findViewById(R.id.focusSec);

        hr.setMaxValue(24);
        hr.setMinValue(0);
        hr.setValue(0);
        min.setMaxValue(59);
        min.setMinValue(0);
        min.setValue(0);
        sec.setMaxValue(59);
        sec.setMinValue(0);
        sec.setValue(0);

        hr.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        min.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        sec.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        final EditText focusName = v.findViewById(R.id.focusName);
        final EditText focusDescription = v.findViewById(R.id.focusDescription);
        Button startFocus = v.findViewById(R.id.btnStartFocus);
        startFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FocusActivity.class);
                intent.putExtra("name", focusName.getText().toString());
                intent.putExtra("description", focusDescription.getText().toString());
                intent.putExtra("hr", hr.getValue());
                intent.putExtra("min", min.getValue());
                intent.putExtra("sec", sec.getValue());
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
