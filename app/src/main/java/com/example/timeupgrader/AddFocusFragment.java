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
import android.widget.Toast;

public class AddFocusFragment extends Fragment {

    NumberPicker hr;
    NumberPicker min;
    NumberPicker sec;
    EditText focusName;
    EditText focusDescription;

    public AddFocusFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_focus, container, false);

        hr = v.findViewById(R.id.focusHr);
        min = v.findViewById(R.id.focusMin);
        sec = v.findViewById(R.id.focusSec);

        hr.setMaxValue(23);
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

        focusName = v.findViewById(R.id.focusName);
        focusDescription = v.findViewById(R.id.focusDescription);
        Button startFocus = v.findViewById(R.id.btnStartFocus);
        startFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focusName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(),"Please enter a valid focus name", Toast.LENGTH_LONG).show();
                }
                else if (hr.getValue() == 0 && min.getValue() == 0 && sec.getValue() == 0) {
                    Toast.makeText(getActivity(),"Please enter a valid time duration", Toast.LENGTH_LONG).show();
                }
                /*else if (hr.getValue() == 0 && min.getValue() < 10) {
                    Toast.makeText(getActivity(),"Focus time can not be less than 10 minutes", Toast.LENGTH_LONG).show();
                }*/
                else {
                    Intent intent = new Intent(getActivity(), FocusActivity.class);
                    intent.putExtra("name", focusName.getText().toString());
                    intent.putExtra("description", focusDescription.getText().toString());
                    intent.putExtra("hr", hr.getValue());
                    intent.putExtra("min", min.getValue());
                    intent.putExtra("sec", sec.getValue());
                    hr.setValue(0);
                    min.setValue(0);
                    sec.setValue(0);
                    focusName.setText("");
                    focusDescription.setText("");
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
