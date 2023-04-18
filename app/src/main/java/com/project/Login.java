//package com.project;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.project.utils.MainApplication;
//import com.project.utils.ViewUtil;
//
//
//public class Login extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
//
//    private EditText et_phone;
//    private EditText et_password;
//    private CheckBox ck_remember;
//    private Button btn_forget;
//    private Button btn_register;
//    private Button btn_login;
//    private boolean bRemember = false;
//    private boolean flag=false;
//     private String username;
//     private String password;
//     private Intent intent;
//    private SharedPreferences mShared;
//    private MainApplication mainApplication;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        et_phone = (EditText) findViewById(R.id.et_phone);
//        et_password = (EditText) findViewById(R.id.et_password);
//        ck_remember = (CheckBox) findViewById(R.id.ck_remember);
//        ck_remember.setOnCheckedChangeListener(new CheckListener());
//        btn_forget = (Button) findViewById(R.id.btn_forget);
//        btn_login = (Button) findViewById(R.id.btn_login);
//        btn_register = (Button) findViewById(R.id.btn_register);
//        ck_remember.setOnCheckedChangeListener(new CheckListener());
//        btn_register.setOnClickListener(this);
//        btn_login.setOnClickListener(this);
//        btn_forget.setOnClickListener(this);
//        et_password.setOnFocusChangeListener(this);
//        et_phone.addTextChangedListener(new HideText(et_phone,et_password));
//
//        /*保留手机号码*/
//        intent=getIntent();
//        username=intent.getStringExtra("username");
//        if(username!=null){
//            et_phone.setText(username);
//        }
//        /*记住密码功能*/
//        mShared = getSharedPreferences("share_login", MODE_PRIVATE);
//        String phone = mShared.getString("phone", "");
//        String password = mShared.getString("password", "");
//       if(!phone.equals("")) {et_phone.setText(phone);}
//        if(!password.equals("")){et_password.setText(password);}
//
//        /*全局传参*/
//        mainApplication=MainApplication.getInstance();
////        reload();
//
//    }
//
//    private  void reload(){
//
//        if(mainApplication.map.get("username")!=null){
//            username=mainApplication.map.get("username");
//            et_phone.setText(username);
//        }
//
//    }
//    @Override
//    public void onClick(View v) {
//
//        username = et_phone.getText().toString();
//        password = et_password.getText().toString();
//        /*注册*/
//        if (v.getId() == R.id.btn_register) {
//            intent = new Intent(this, LoginForgetActivity.class);
//            intent.putExtra("username",username);
//            intent.putExtra("flag",true);
//            startActivity(intent);
//        }
//        /*登录*/
//        else if (v.getId() == R.id.btn_login) {
//            /*保存用户名为全局变量*/
////            mainApplication.map.put("username",username);
//            flag=true;
//            if (bRemember) {
//                SharedPreferences.Editor editor = mShared.edit();
//                editor.putString("phone", et_phone.getText().toString());
//                editor.putString("password", et_password.getText().toString());
//                editor.commit();
//            }else {
//                SharedPreferences.Editor editor = mShared.edit();
//                editor.putString("phone","");
//                editor.putString("password", "");
//                editor.commit();
//            }
//
//        }
//        /*忘记密码*/
//        else if (v.getId() == R.id.btn_forget) {
//            intent = new Intent(this, LoginForgetActivity.class);
//            intent.putExtra("username",username);
//            intent.putExtra("flag",false);
//            startActivity(intent);
//        }
//    }
//
//
//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        if (hasFocus) {
//            String phone = et_phone.getText().toString();
//            if (TextUtils.isEmpty(phone) || phone.length() < 11) {
//                et_phone.requestFocus();
//                Toast.makeText(this, "请输入11位手机号码", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    /*是否勾选“记住我”按钮*/
//    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if (buttonView.getId() == R.id.ck_remember) {
//                bRemember = isChecked;
//            }
//        }
//    }
//
//    //从修改密码页面返回登录页面，要清空密码的输入框
//    @Override
//    protected void onRestart() {
//        et_password.setText("");
//        super.onRestart();
//    }
//
//    /*监听、隐藏软键盘*/
//    private class HideText extends Activity implements TextWatcher {
//
//        private EditText v;
//        private View nextview;
//        private CharSequence str;
//        private int maxlength;
//        public HideText(EditText editText,View vnext){
//            super();
//            v=editText;
//            nextview=vnext;
//            maxlength= ViewUtil.getMaxLength(editText);
//        }
//
//
//        /*返回当前页面的activity*/
//
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            str = s;
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//            if (str == null || str.length() == 0) {
//                return;
//            }
//            if (str.length() == 11 && maxlength==11) {
//                ViewUtil.hideMethod(Login.this,v);
//                nextview.requestFocus();
//            }
//            if (str.length() == 6 && maxlength==6) {
//                ViewUtil.hideMethod(Login.this,v);
//                nextview.requestFocus();
//            }
//        }
//
//
//    }
//}