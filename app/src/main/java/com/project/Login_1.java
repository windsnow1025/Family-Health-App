package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


public class Login_1 extends AppCompatActivity implements View.OnClickListener {

    private EditText et_phone;
    private EditText et_password;
    private CheckBox ck_remember;
    private Button btn_forget;
    private Button btn_register;
    private Button btn_login;
    private boolean bRemember = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        ck_remember = (CheckBox) findViewById(R.id.ck_remember);
        btn_forget = (Button) findViewById(R.id.btn_forget);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        ck_remember.setOnCheckedChangeListener(new CheckListener());
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        if (v.getId() == R.id.btn_register) {
            Intent intent = new Intent(this, LoginForgetActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_login) {
         
        } else if (v.getId()== R.id.btn_forget) {
            
        }
    }

    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.ck_remember) {
                bRemember = isChecked;
            }
        }
    }

    //从修改密码页面返回登录页面，要清空密码的输入框
    @Override
    protected void onRestart() {
        et_password.setText("");
        super.onRestart();
    }
}