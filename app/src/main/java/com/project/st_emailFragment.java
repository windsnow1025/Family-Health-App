package com.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.JDBC.UserDao;
import com.project.Sqlite.UserLocalDao;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class st_emailFragment extends Fragment {



    private Button bt_back;
    private Button bt_set;
    private EditText et_email;
    private TextView tv_email;
    private String format = "\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
    private String email;
    private UserDao userDao;
    private UserLocalDao userLocalDao;
    private String username;
    public st_emailFragment() {
        // Required empty public constructor
    }

    private void init(View view){
        tv_email=view.findViewById(R.id.tv_email);
        tv_email.setText(userLocalDao.getUserInfo(username).getEmail());
        et_email=view.findViewById(R.id.et_email);
        bt_back=view.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new BTlistener());
        bt_set=view.findViewById(R.id.bt_set);
        bt_set.setOnClickListener(new BTlistener());
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_st_email, container, false);
        userDao = new UserDao();
        userLocalDao = new UserLocalDao(getActivity().getApplicationContext());
        userLocalDao.open();
        username=userLocalDao.getUser();
        init(view);

        return view;
    }
    private class BTlistener implements View.OnClickListener{

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_back:
                    getParentFragmentManager().popBackStack();
                    break;
                case R.id.bt_set:
                    email=et_email.getText().toString();
                    if (!email.matches(format)) {
                        Toast.makeText(getContext(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        @SuppressLint("DefaultLocale") String mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
                        Toast.makeText(getContext(), "验证码为："+mVerifyCode, Toast.LENGTH_LONG).show();
                        final EditText inputServer = new EditText(getContext());
                        inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("请输入验证码").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer);
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String _sign = inputServer.getText().toString();
                                if (_sign.equals(mVerifyCode)) {
                                    tv_email.setText(email);
                                    et_email.setText("");
                                    et_email.clearFocus();
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("email", email);
                                    try {
                                        userDao.updateUserInformation(username, hashMap);
                                        userLocalDao.addOrUpdateUser(userDao.getUserInformation(username));
                                    } catch (TimeoutException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Toast.makeText(getContext(), "验证码修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "验证码为空", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.show();
                    }
                    break;
                default:
                    break;
            }

        }
    }
}