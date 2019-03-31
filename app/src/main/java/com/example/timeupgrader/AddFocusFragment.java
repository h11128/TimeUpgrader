package com.example.timeupgrader;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

        hr.setMaxValue(11);
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
                    if (hasPermission(getContext())) {
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
                    else {
                        showPermissionDialog();
                    }
                }
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean hasPermission(Context context) {
        /*PackageManager pm = getActivity().getPackageManager();
        return PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.PACKAGE_USAGE_STATS", "com.example.timeupgrader");*/
        /*int perm = context.checkCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS");
        return perm == PackageManager.PERMISSION_GRANTED;*/
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
        /*UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 30 * 1000, time);
        if (stats == null || stats.size() == 0) return false;
        else return true;*/
    }

    private void showPermissionDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("Permission required");
        normalDialog.setMessage("TimeUpgrade needs your permission to access app usage stats to make sure you have not switched to other apps during focus mode.");
        normalDialog.setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                dialog.dismiss();
            }
        });
        normalDialog.create().show();
    }
}
