package com.example.timeupgrader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public class FocusFragment extends Fragment {

    public FocusFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_focus, container, false);

        NumberPicker hr = v.findViewById(R.id.focusHr);
        NumberPicker min = v.findViewById(R.id.focusMin);
        NumberPicker sec = v.findViewById(R.id.focusSec);

        hr.setMaxValue(24);
        hr.setMinValue(0);
        hr.setValue(1);
        min.setMaxValue(59);
        min.setMinValue(0);
        min.setValue(0);
        sec.setMaxValue(59);
        sec.setMinValue(0);
        sec.setValue(0);

        hr.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        min.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        sec.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
