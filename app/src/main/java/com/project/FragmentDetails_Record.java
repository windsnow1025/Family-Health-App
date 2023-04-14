package com.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FragmentDetails_Record extends Fragment implements DatePickerDialog.OnDateSetListener {
    private int i;
    private  List<Info> infoList;
    private Button rStartAlarm;
    private Button bt_rcancel;

    private EditText ret_title;
    private TextView ret_time;
    private TextView rtv_time;
    private TextView rtv_date;
    private TextView rtv_part;
    private TextView rtv_advice;
    private String[] details;
    InfoAdapter adapter;
    private boolean flag;

    /*用于新建*/
    public FragmentDetails_Record(String[] strings, InfoAdapter infoAdapter) {
        this.details = strings;
        this.adapter = infoAdapter;
    }

    /*用于修改*/
    public FragmentDetails_Record(InfoAdapter infoAdapter, List<Info> Infolist, int I, boolean Flag) {
        this.adapter = infoAdapter;
        this.flag = Flag;
        this.infoList = Infolist;
        this.i = I;
    }

    private void init(View view) {
        rStartAlarm = view.findViewById(R.id.rStartAlarm);
        if (flag) {
            rStartAlarm.setText("修改");
        }
        rtv_time = view.findViewById(R.id.rtv_time);
        rtv_part = view.findViewById(R.id.rtv_part);
        rtv_date = view.findViewById(R.id.rtv_date);
        rtv_advice = view.findViewById(R.id.rtv_advice);
        ret_title = view.findViewById(R.id.ret_title);
        ret_time = view.findViewById(R.id.ret_time);
        bt_rcancel=view.findViewById(R.id.bt_cancel);
    }

    /*获取数据*/
    private void infoSet(boolean flag) {
        rtv_time.setText("details[0]");
        rtv_part.setText("details[1]");
        rtv_advice.setText("details[2]");
        rtv_advice.setText("");
        /*表修改状态，非新增时*/
        if (flag) {
            ret_title.setText("test复诊");
            ret_time.setText("1-1");
            rtv_date.setText("1-1");
                }
            }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details__record, container, false);
        init(view);
        infoSet(flag);

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
                    Info info = new Info(ret_title.getText().toString(), rtv_date.getText().toString(), ret_time.getText().toString(), false);
                    adapter.add(info);
                    if (flag) {
                        infoList.remove(i);
                    }
                    if (!flag) {
                        Toast.makeText(getContext(), "提醒添加成功", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getContext(), "提醒修改成功", Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().popBackStack();
                }else Toast.makeText(getContext(), "信息不完整", Toast.LENGTH_SHORT).show();
            }
        });
        bt_rcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("取消修改？");
                builder.setMessage("取消编辑将返回原界面，本次修改将不被保存！");
                builder.setNegativeButton("继续编辑", null);
                builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = new FragmentAlert();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                builder.show();
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