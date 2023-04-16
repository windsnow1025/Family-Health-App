package com.project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class st_nameFragment extends Fragment {

    private Button bt_back;
    private Button bt_set;
    private EditText et_name;
    private TextView tv_name;
    private UserDao userDao;
    private UserLocalDao userLocalDao;
    private String username;
    public st_nameFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    private void init(View view){
        tv_name=view.findViewById(R.id.tv_username);
        et_name=view.findViewById(R.id.et_username);
        bt_back=view.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new BTlistener());
        bt_set=view.findViewById(R.id.bt_set);
        bt_set.setOnClickListener(new BTlistener());
    }
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_name, container, false);
        userDao = new UserDao();
        userLocalDao = new UserLocalDao(getActivity().getApplicationContext());
        userLocalDao.open();
        username=userLocalDao.getUser();
        init(view);
        tv_name.setText(userLocalDao.getUserInfo(username).getUsername()+"");
        return view;
    }
    private class BTlistener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_back:
                    getParentFragmentManager().popBackStack();
                    break;
                case R.id.bt_set:
                    String username1=et_name.getText().toString();
                    if(!username1.equals("")){
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("username", username1);
                        try {
                            try {
                                userDao.updateUserInformation(username, hashMap);
                                userLocalDao.addOrUpdateUser(userDao.getUserInformation(username));
                            } catch (TimeoutException e) {
                                throw new RuntimeException(e);
                            }
                            Toast.makeText(getContext(), "用户名称修改成功", Toast.LENGTH_SHORT).show();
                            tv_name.setText(username1);
                            et_name.setText("");
                            et_name.clearFocus();
                        } finally {

                        }
                    }
                    break;
                default:
                    break;
            }

        }
    }
}