package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.project.JDBC.UserDao;
import com.project.Pojo.UserInfo;
import com.project.Sqlite.UserLocalDao;
import com.project.utils.ViewUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;


public class LoginForgetActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener, View.OnFocusChangeListener {

    private EditText et_usename;
    private EditText et_password;
    private EditText et_birth;
    private EditText et_verifycode;
    private String mVerifyCode;
    private Intent intent;
    private String username;
    private String password;
    private String birth;
    private String sex;
    private Boolean flag = false;
    private UserDao userDao;
    private UserLocalDao userLocalDao;

    public LoginForgetActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = new UserDao();
        userLocalDao = new UserLocalDao(getApplicationContext());
        userLocalDao.open();
        intent = getIntent();
        username = intent.getStringExtra("username");
        flag = intent.getBooleanExtra("flag", false);
        /*注册*/
        if (flag) {
            setContentView(R.layout.activity_login_forget);
            et_birth = (EditText) findViewById(R.id.et_birth);
            et_birth.setOnClickListener(new View.OnClickListener() {//设置监听器，打开日期控件
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(LoginForgetActivity.this, LoginForgetActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                    dialog.show();
                }
            });

        } else /*忘记密码*/ {
            setContentView(R.layout.activity_login_forget_1);
        }
        et_usename = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_verifycode = (EditText) findViewById(R.id.et_verifycode);
        findViewById(R.id.btn_getcode).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        et_usename.addTextChangedListener(new HideText(et_usename, et_password));
        et_password.setOnFocusChangeListener(this);
        et_verifycode.addTextChangedListener(new HideText(et_verifycode, findViewById(R.id.btn_confirm)));

        if (username != null) {
            et_usename.setText(username);
        }


    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        username = et_usename.getText().toString();
        if (v.getId() == R.id.btn_getcode && !username.equals("") && flag) {
            try {
                /**
                 * 判断账号是否已存在
                 **/
                if (!userDao.checkUserUnique(username)) {
                    mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginForgetActivity.this);
                    builder.setTitle("请记住验证码");
                    builder.setMessage("手机号" + et_usename.getText().toString() + "，本次验证码是" + mVerifyCode + "，请输入验证码");
                    builder.setPositiveButton("好的", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Toast.makeText(this, "该手机号已注册", Toast.LENGTH_SHORT).show();
                }
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        } else if (v.getId() == R.id.btn_getcode && !username.equals("") && !flag) {
            mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginForgetActivity.this);
            builder.setTitle("请记住验证码");
            builder.setMessage("手机号" + et_usename.getText().toString() + "，本次验证码是" + mVerifyCode + "，请输入验证码");
            builder.setPositiveButton("好的", null);
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.btn_confirm) {

            username = et_usename.getText().toString();
            password = et_password.getText().toString();
            if (flag) {
                birth = et_birth.getText().toString();
                sex = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId())).getText().toString();
            }
            if (username.equals("") || password.equals("")) {
                Toast.makeText(this, "用户名、密码不能为空", Toast.LENGTH_SHORT).show();
            } else if (!et_verifycode.getText().toString().equals(mVerifyCode)) {
                Toast.makeText(this, "验证码错误，清重新输入", Toast.LENGTH_SHORT).show();
            } else {
                if (flag) {
                    if (birth.equals("")) {
                        Toast.makeText(this, "请选择生日", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (sex.equals("")) {
                        Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        /**
                         * 插入账号
                         **/
                        if (userDao.insertUser(username, password, sex, birth).equals(username)) {
                            Toast.makeText(this, "恭喜您注册成功，清前往登录！", Toast.LENGTH_SHORT).show();
                            intent = new Intent(this, Login_1.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        } else Toast.makeText(this, "网络请求超时，请稍后重试", Toast.LENGTH_SHORT).show();
                    } catch (TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    /**
                     * 修改密码
                     **/
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("password", password);
                    try {
                        if (userDao.updateUserInformation(username, hashMap)) {
                            Toast.makeText(this, "密码已修改成功，请重新登录！", Toast.LENGTH_SHORT).show();
                            intent = new Intent(this, Login_1.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        } else Toast.makeText(this, "网络请求超时，请稍后重试", Toast.LENGTH_SHORT).show();
                    } catch (TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
    }

    /*判断用户名是否合法*/
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            String phone = et_usename.getText().toString();
            if (TextUtils.isEmpty(phone) || phone.length() < 11) {
                et_usename.requestFocus();
                Toast.makeText(this, "请输入11位手机号码", Toast.LENGTH_SHORT).show();
            }
        }
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
        if (date.getTime() > date2.getTime()) {
            et_birth.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        } else {
            Toast.makeText(this, "出生日期不能小于当前日期", Toast.LENGTH_SHORT).show();
        }



    }

    /*监听、隐藏软键盘*/
    private class HideText extends Activity implements TextWatcher {

        private EditText v;
        private View nextview;
        private CharSequence str;
        private int maxlength;

        public HideText(EditText editText, View vnext) {
            super();
            v = editText;
            nextview = vnext;
            maxlength = ViewUtil.getMaxLength(editText);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            str = s;
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (str == null || str.length() == 0) {
                return;
            }
            if (str.length() == 11 && maxlength == 11) {
                ViewUtil.hideMethod(LoginForgetActivity.this, v);
                nextview.requestFocus();
            }
            if (str.length() == 6 && maxlength == 6) {
                ViewUtil.hideMethod(LoginForgetActivity.this, v);
                nextview.requestFocus();
            }
        }


    }
}