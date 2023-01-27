package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.SQLOutput;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_usename;
    private EditText et_password;
    private EditText et_verifycode;
    private String mVerifyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        et_usename = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_verifycode = (EditText) findViewById(R.id.et_verifycode);
        findViewById(R.id.btn_getcode).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_getcode && !et_usename.getText().toString().equals("")) {
            mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginForgetActivity.this);
            builder.setTitle("请记住验证码");
            builder.setMessage("手机号" + et_usename.getText().toString() + "，本次验证码是" + mVerifyCode + "，请输入验证码");
            builder.setPositiveButton("好的", null);
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.btn_confirm) {
            String username = et_usename.getText().toString();
            String password= et_password.getText().toString();
            Intent intent=new Intent(this,Login_1.class);
            startActivity(intent);
        }
    }
}