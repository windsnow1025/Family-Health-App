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

import com.project.JDBC.AlertDao;
import com.project.JDBC.HistoryDao;
import com.project.JDBC.ReportDao;
import com.project.Pojo.Alert;
import com.project.Pojo.History;
import com.project.Pojo.Report;
import com.project.Sqlite.UserLocalDao;
import com.project.utils.Info;
import com.project.utils.InfoAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class FragmentDetails_Record extends Fragment implements DatePickerDialog.OnDateSetListener {
    private int i;
    private  List<Info> infoList;
    private Button rStartAlarm;
    private Button bt_rcancel;

    private EditText ret_title;
    private TextView ret_time;
    private TextView rtv_time;
    private TextView rtv_date;
    private TextView rtv_hospital;
    private TextView rtv_part;
    private EditText rtv_advice;
    InfoAdapter adapter;
    private boolean flag;
    private ArrayList<Report> reportArrayList;
    private ArrayList<History> historyArrayList;
    private ArrayList<Alert> alertArrayList;
    private UserLocalDao userLocalDao;
    private ReportDao reportDao;
    private HistoryDao historyDao;
    private AlertDao alertDao;
    private History history;
    private Report report;
    private Alert alert;
    private String userID;
    private int num,num_alerk;
    private boolean isreport;
    private boolean ismedicine;

    /*用于新建*/
    public FragmentDetails_Record(boolean ismedicine,int n, boolean isreport, InfoAdapter infoAdapter) {
        this.num = n;
        this.ismedicine=ismedicine;
        this.adapter = infoAdapter;
        this.isreport = isreport;//是否为报告
    }

    /*用于修改*/
    public FragmentDetails_Record(boolean ismedicine,int n,InfoAdapter infoAdapter, List<Info> Infolist, int I,boolean isreport, boolean Flag) {
        this.adapter = infoAdapter;
        this.num = n;//num为捆绑的编号
        this.ismedicine=ismedicine;
        this.isreport = isreport;//是否为报告
        this.flag = Flag;//是否为修改
        this.infoList = Infolist;
        this.i = I;//i为闹钟编号
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
        rtv_hospital=view.findViewById(R.id.rtv_hospital);
        if (isreport) report = UserLocalDao.gerReport(reportArrayList, num);
        else history = UserLocalDao.getHistory(historyArrayList, num);
        num_alerk=userLocalDao.getAlertList(userID).size();
   }

    /*获取数据*/
    @SuppressLint("SetTextI18n")
    private void infoSet(boolean flag) {
        if(isreport){
            rtv_time.setText(report.getReport_date()+"");
            rtv_part.setText(report.getReport_type()+"");
            rtv_advice.setText(report.getReport_content()+"");
            rtv_hospital.setText(report.getReport_place()+"");
        }else {
            rtv_time.setText(history.getHistory_date()+"");
            rtv_part.setText(history.getHistory_organ()+"");
            rtv_advice.setText(history.getSuggestion()+"");
            rtv_hospital.setText(history.getHistory_place()+"");
        }
        /*表修改状态，非新增时*/
        if (flag) {
            Alert alert1=userLocalDao.getAlert(alertArrayList,i);
            ret_title.setText(alert1.getContent());
            ret_time.setText(alert1.getDate());
            rtv_date.setText(alert1.getCycle());
                }
            }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details__record, container, false);
        userLocalDao = new UserLocalDao(getActivity().getApplicationContext());
        userLocalDao.open();
        userID = userLocalDao.getUser();
        alertArrayList=userLocalDao.getAlertList(userID);
        reportDao = new ReportDao();
        historyDao = new HistoryDao();
        alertDao=new AlertDao();
        try {
            reportArrayList = reportDao.getReportList(userID);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        try {
            historyArrayList = historyDao.getHistoryList(userID);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
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
                    Info info = new Info(ismedicine,ret_title.getText().toString(), rtv_date.getText().toString(), ret_time.getText().toString(), true, num,num_alerk);
                    alert=new Alert(num_alerk,String.valueOf(ismedicine),userID,ret_time.getText().toString(),rtv_date.getText().toString(),ret_title.getText().toString(),String.valueOf(isreport),num,"false");
//                   是否为修改
                    if (flag) {
                        alert=new Alert(i,String.valueOf(ismedicine),userID,ret_time.getText().toString(),rtv_date.getText().toString(),ret_title.getText().toString(),String.valueOf(isreport),num,"false");
                        userLocalDao.updateAlert(userID,alert);
                        try {
                            alertDao.updateAlert(userID,alert);
                        } catch (TimeoutException e) {
                            throw new RuntimeException(e);
                        }
//
                    }else {
                        userLocalDao.insertAlert(userID, alert);
                        try {
                            alertDao.insertAlert(userID,alert);
                        } catch (TimeoutException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    adapter.add(info);
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

        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = year + "-" + (month + 1) + "-" + dayOfMonth;
        System.out.println(dateString);
        Date date2 = null;
        try {
            date2 = sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (date.getTime() < date2.getTime()) {
            month += 1;
            rtv_date.setText(year + "-" + month + "-" + dayOfMonth);
        } else {
            Toast.makeText(getContext(), "提醒日期不能早于当前日期", Toast.LENGTH_SHORT).show();
        }


    }
}