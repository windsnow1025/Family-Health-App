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

public class st_nameFragment extends Fragment {

    private Button bt_back;
    private Button bt_set;
    private EditText et_name;
    private TextView tv_name;
    public st_nameFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_st_name, container, false);
        tv_name=view.findViewById(R.id.tv_username);
        et_name=view.findViewById(R.id.et_username);
        bt_back=view.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new BTlistener());
        bt_set=view.findViewById(R.id.bt_set);
        bt_set.setOnClickListener(new BTlistener());

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
                    String username=et_name.getText().toString();
                    if(!username.equals("")){
                        Toast.makeText(getContext(), "用户名称修改成功", Toast.LENGTH_SHORT).show();
                        tv_name.setText(username);
                        et_name.setText("");
                        et_name.clearFocus();
                    }
                    break;
                default:
                    break;
            }

        }
    }
}