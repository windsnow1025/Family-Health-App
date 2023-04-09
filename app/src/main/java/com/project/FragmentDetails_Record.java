package com.project;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.utils.Info;
import com.project.utils.InfoAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentDetails_Record extends Fragment implements DatePickerDialog.OnDateSetListener {
    private Button rStartAlarm;

    private EditText ret_title;
    private TextView ret_time;
    private TextView rtv_time;
    private TextView rtv_date;
    private TextView rtv_part;
    private TextView rtv_advice;
    private String[] details;
    InfoAdapter adapter;

    public FragmentDetails_Record(String[] strings, InfoAdapter infoAdapter) {
        this.details = strings;
        this.adapter = infoAdapter;
    }

    private void init(View view) {
        rStartAlarm = view.findViewById(R.id.rStartAlarm);
        rtv_time = view.findViewById(R.id.rtv_time);
        rtv_part = view.findViewById(R.id.rtv_part);
        rtv_date = view.findViewById(R.id.rtv_date);
        rtv_advice = view.findViewById(R.id.rtv_advice);
        ret_title = view.findViewById(R.id.ret_title);
        rtv_time.setText(details[0]);
        rtv_part.setText(details[1]);
        rtv_advice.setText(details[2]);
        ret_time = view.findViewById(R.id.ret_time);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details__record, container, false);
        init(view);

        rtv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener) FragmentDetails_Record.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                dialog.show();
            }
        });
        ret_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setAlarmTime(hourOfDay, minute);
                        String str = hourOfDay + ":" + minute;
                        ret_time.setText(str);
                    }
                };

                new TimePickerDialog(getContext(), 3, onTimeSetListener, 11, 11, true).show();
            }
        });
        rStartAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ret_title.getText().toString().equals("") && !rtv_date.getText().toString().equals("") && !ret_time.getText().toString().equals("")) {
                    Info info = new Info(ret_title.getText().toString(), rtv_date.getText().toString(), ret_time.getText().toString(), true);
                    adapter.add(info);
                    Toast.makeText(getContext(), "提醒添加成功", Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().popBackStack();
                }else Toast.makeText(getContext(), "信息不完整", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    @SuppressLint("QueryPermissionsNeeded")
    private void setAlarmTime(int H, int M) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SET_ALARM");
        //获取用户输入的时间及标题
        String Title = ret_title.getText().toString();


        //设置闹钟时间

        intent.putExtra(AlarmClock.EXTRA_HOUR, H);//设置时钟
        intent.putExtra(AlarmClock.EXTRA_MINUTES, M);//设置分钟
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, Title);//设置闹钟标题
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);//跳过UI

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month += 1;
        rtv_date.setText(year + "-" + month + "-" + dayOfMonth);
    }
}