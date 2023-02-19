package com.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class personalCenter extends Fragment {

    private Button bt_back;
    private Button bt_username;
    private Button bt_password;
    private Button bt_email;
    private Button bt_exit;
    private EditText et_username;
    private EditText et_password;
    private EditText et_email;
    private String username;
    private String password;
    private String email;

    public personalCenter() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        et_username=view.findViewById(R.id.et_username);
        et_password=view.findViewById(R.id.et_password);
        et_email=view.findViewById(R.id.et_email);
        bt_username = view.findViewById(R.id.bt_username);
        bt_password = view.findViewById(R.id.bt_password);
        bt_email = view.findViewById(R.id.bt_email);
        bt_username.setOnClickListener(new btListener());
        bt_password.setOnClickListener(new btListener());
        bt_email.setOnClickListener(new btListener());
        bt_back = view.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new btListener());
        bt_exit=view.findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(new btListener());


        return view;
    }
    public class btListener implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            username=et_username.getText().toString();
            password=et_password.getText().toString();
            email=et_email.getText().toString();
            switch (v.getId()) {
                case R.id.bt_username:
                    System.out.println(username);
                    break;
                case R.id.bt_password:
                    System.out.println(password);
                    break;
                case R.id.bt_email:
                    System.out.println(email);
                    break;
                case R.id.bt_back:
                    getParentFragmentManager().popBackStack();
                    break;
                case R.id.bt_exit:
                   Intent intent = new Intent(getActivity(), Login.class);
                   startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}