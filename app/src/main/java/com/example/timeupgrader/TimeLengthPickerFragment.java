package com.example.timeupgrader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;


public class TimeLengthPickerFragment extends Fragment {
    NumberPicker day;
    NumberPicker hr;
    NumberPicker min;

    private OnFragmentInteractionListener mListener;

    public TimeLengthPickerFragment() {}


    // TODO: Rename and change types and number of parameters
    public static TimeLengthPickerFragment newInstance(String param1, String param2) {
        TimeLengthPickerFragment fragment = new TimeLengthPickerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_time_length_picker,container,false);
        day = v.findViewById(R.id.lengthDay);
        hr = v.findViewById(R.id.lengthHour);
        min = v.findViewById(R.id.lengthMinute);
        day.setMaxValue(30);
        day.setMinValue(0);
        day.setValue(0);
        hr.setMaxValue(23);
        hr.setMinValue(0);
        hr.setValue(0);
        min.setMaxValue(59);
        min.setMinValue(0);
        min.setValue(0);

        day.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        hr.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        min.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        long duration = (((day.getValue() * 24) + hr.getValue()) * 60 + min.getValue()) *60 *1000;


        Button setOk = v.findViewById(R.id.btnOK);
        setOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(day.getValue() == 0 && hr.getValue() == 0 && min.getValue() == 0) {
                    Toast.makeText(getActivity(),"Please enter a valid time duration", Toast.LENGTH_LONG).show();
                }
            }
        });
        return inflater.inflate(R.layout.fragment_time_length_picker, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
