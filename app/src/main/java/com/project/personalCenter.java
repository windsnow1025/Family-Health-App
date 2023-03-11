package com.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import java.util.Calendar;


public class personalCenter extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Button bt_back;
    private Button bt_username;
    private Button bt_password;
    private Button bt_email;
    private Button bt_exit;
    private Button bt_age;


    private EditText et_password;

    private TextView tv_age;

    private int age;
    private Boolean flag = false;
    private FragmentTransaction transaction;


    public personalCenter() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        et_password = view.findViewById(R.id.et_password);
        bt_username = view.findViewById(R.id.bt_username);
        bt_password = view.findViewById(R.id.bt_password);
        bt_email = view.findViewById(R.id.bt_email);
        bt_username.setOnClickListener(new btListener());
        bt_password.setOnClickListener(new btListener());
        bt_email.setOnClickListener(new btListener());
        bt_back = view.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new btListener());
        bt_exit = view.findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(new btListener());
        tv_age = view.findViewById(R.id.tv_age);
        bt_age = view.findViewById(R.id.bt_age);

        /* 重置年龄*/
        bt_age.setOnClickListener(new View.OnClickListener() {//设置监听器，打开日期控件
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getContext(), personalCenter.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                dialog.show();
            }
        });


        return view;
    }

    /*设置年龄信息*/
    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        age = calendar.get(Calendar.YEAR) - year;
        if (age > 0 && age < 150) {
            tv_age.setText(age + "");
        }
    }

    private class btListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_username:
                     transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new st_nameFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.bt_password:
                     transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new st_passFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.bt_email:
                    transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new st_emailFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.bt_back:
                    getParentFragmentManager().popBackStack();
                    break;
                case R.id.bt_exit:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("退出登录？");
                    builder.setMessage("请问您确定要退出当前登录吗？");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), Login_1.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                    break;
                default:
                    break;
            }
        }
    }
}