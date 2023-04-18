package com.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.JDBC.AlertDao;
import com.project.JDBC.HistoryDao;
import com.project.JDBC.ReportDao;
import com.project.JDBC.UserDao;
import com.project.Pojo.Alert;
import com.project.Pojo.History;
import com.project.Pojo.Report;
import com.project.Sqlite.UserLocalDao;
import com.project.utils.Info;
import com.project.utils.InfoAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class FragmentDetails extends Fragment {

    private final ArrayList<Integer> Time = new ArrayList<Integer>();
    private List<Info> infoList;
    private CheckBox Monday;
    private CheckBox Tuesday;
    private CheckBox Wednesday;
    private CheckBox Thursday;
    private CheckBox Friday;
    private CheckBox Saturday;
    private CheckBox Sunday;

    private Button StartAlarm;
    private Button bt_cancel;

    private EditText et_title;
    private TextView et_time;
    private TextView tv_time;
    private TextView tv_hospital;
    private TextView tv_part;
    private TextView tv_advice;
    private int H, M,i;
    InfoAdapter adapter;
    private boolean flag = false;
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
    public FragmentDetails(boolean ismedicine,int n, boolean isreport, InfoAdapter infoAdapter) {
        this.num = n;//num为捆绑的编号
        this.adapter = infoAdapter;
        this.ismedicine=ismedicine;
        this.isreport = isreport;//是否为报告
    }

    /*用于修改*/
    public FragmentDetails(boolean ismedicine,int n,InfoAdapter infoAdapter, List<Info> Infolist, int I,boolean isreport, boolean Flag) {
        this.adapter = infoAdapter;
        this.ismedicine=ismedicine;
        this.num = n;//num为捆绑的编号
        this.isreport = isreport;//是否为报告
        this.flag = Flag;//是否为修改
        this.infoList = Infolist;
        this.i = I;//i为闹钟编号
    }

    private void init(View view) {
        Monday = view.findViewById(R.id.Monday);
        Tuesday = view.findViewById(R.id.Tuesday);
        Wednesday = view.findViewById(R.id.Wednesday);
        Thursday = view.findViewById(R.id.Thursday);
        Friday = view.findViewById(R.id.Friday);
        Saturday = view.findViewById(R.id.Saturday);
        Sunday = view.findViewById(R.id.Sunday);
        StartAlarm = view.findViewById(R.id.StartAlarm);
        if (flag) {
            StartAlarm.setText("修改");
        }
        tv_time = view.findViewById(R.id.tv_time);
        tv_part = view.findViewById(R.id.tv_part);
        tv_hospital = view.findViewById(R.id.tv_hospital);
        tv_advice = view.findViewById(R.id.tv_advice);
        et_title = view.findViewById(R.id.et_title);
        et_time = view.findViewById(R.id.et_time);
        bt_cancel = view.findViewById(R.id.bt_cancel);

        if (isreport) report = UserLocalDao.gerReport(reportArrayList, num);
        else{
            history = UserLocalDao.getHistory(historyArrayList, num);}
        num_alerk=userLocalDao.getAlertList(userID).size();
    }

    /*数据导入*/
    @SuppressLint("SetTextI18n")
    private  void infoSet(boolean flag) {
        if(isreport){
            tv_time.setText(report.getReport_date()+"");
            tv_part.setText(report.getReport_type()+"");
            tv_advice.setText(report.getReport_content()+"");
            tv_hospital.setText(report.getReport_place()+"");
        }else {
            tv_time.setText(history.getHistory_date()+"");
            tv_part.setText(history.getHistory_organ()+"");
            tv_advice.setText(history.getSuggestion()+"");
            tv_hospital.setText(history.getHistory_place()+"");
        }

        /*表修改状态，非新增时*/
        if (flag) {
            Alert alert1=userLocalDao.getAlert(alertArrayList,i);
            et_title.setText(alert1.getContent());
            et_time.setText(alert1.getDate());
          String[] newArray = alert1.getCycle().split("\\s");
            for (String str : newArray) {
                switch (str) {
                    case "周日":
                        Sunday.setChecked(true);
                        break;
                    case "周一":
                        Monday.setChecked(true);
                        break;
                    case "周二":
                        Tuesday.setChecked(true);
                        break;
                    case "周三":
                        Wednesday.setChecked(true);
                        break;
                    case "周四":
                        Thursday.setChecked(true);
                        break;
                    case "周五":
                        Friday.setChecked(true);
                        break;
                    case "周六":
                        Saturday.setChecked(true);
                        break;
                }
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
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
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        H = hourOfDay;
                        M = minute;
                        String str = hourOfDay + ":" + minute;
                        et_time.setText(str);
                    }
                };

                new TimePickerDialog(getContext(), 3, onTimeSetListener, 11, 11, true).show();
            }
        });
        /*创建闹钟*/
        StartAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarmTime(H, M);
                if (!et_title.getText().toString().equals("") && !Time.isEmpty() && !et_time.getText().toString().equals("")) {
                    Info info = new Info(ismedicine,et_title.getText().toString(), getDate(Time), et_time.getText().toString(), true, num,num_alerk);
                   alert=new Alert(num_alerk,String.valueOf(ismedicine),userID,et_time.getText().toString(),getDate(Time),et_title.getText().toString(),String.valueOf(isreport),num,"false");
                    System.out.println("编号"+num_alerk);

//                   是否为修改
                    if (flag) {
                        alert=new Alert(i,String.valueOf(ismedicine),userID,et_time.getText().toString(),getDate(Time),et_title.getText().toString(),String.valueOf(isreport),num,"false");
                        System.out.println("编号"+i);
                        userLocalDao.updateAlert(userID,alert);
                        try {
                            alertDao.updateAlert(userID,alert);
                        } catch (TimeoutException e) {
                            throw new RuntimeException(e);
                        }

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
                } else Toast.makeText(getContext(), "信息不完整", Toast.LENGTH_SHORT).show();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
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

    static String getDate(ArrayList<Integer> Time) {

        StringBuilder str = new StringBuilder();
        for (Integer i : Time) {
            switch (i) {
                case 1:
                    str.append("周天 ");
                    break;
                case 2:
                    str.append("周一 ");
                    break;
                case 3:
                    str.append("周二 ");
                    break;
                case 4:
                    str.append("周三 ");
                    break;
                case 5:
                    str.append("周四 ");
                    break;
                case 6:
                    str.append("周五 ");
                    break;
                case 7:
                    str.append("周六 ");
                    break;
            }
        }

        return str.toString();
    }


    @SuppressLint("QueryPermissionsNeeded")
    private void setAlarmTime(int H, int M) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SET_ALARM");
        //获取用户输入的时间及标题
        String Title = et_title.getText().toString();
        Time.clear();

        if (Monday.isChecked())
            Time.add(Calendar.MONDAY);
        if (Tuesday.isChecked())
            Time.add(Calendar.TUESDAY);
        if (Wednesday.isChecked())
            Time.add(Calendar.WEDNESDAY);
        if (Thursday.isChecked())
            Time.add(Calendar.THURSDAY);
        if (Friday.isChecked())
            Time.add(Calendar.FRIDAY);
        if (Saturday.isChecked())
            Time.add(Calendar.SATURDAY);
        if (Sunday.isChecked())
            Time.add(Calendar.SUNDAY);

        //设置闹钟时间
        intent.putExtra(AlarmClock.EXTRA_DAYS, Time);//设置闹钟日子
        intent.putExtra(AlarmClock.EXTRA_HOUR, H);//设置时钟
        intent.putExtra(AlarmClock.EXTRA_MINUTES, M);//设置分钟
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, Title);//设置闹钟标题
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);//跳过UI

    }

}