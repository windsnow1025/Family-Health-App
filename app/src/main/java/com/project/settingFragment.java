package com.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class settingFragment extends Fragment {
private Button bt_back;
private Button bt_darkcolor;
private Button bt_lightcolor;

    public settingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        bt_back=view.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new BTlistener());
        bt_darkcolor=view.findViewById(R.id.bt_darkcolor);
        bt_darkcolor.setOnClickListener(new BTlistener());
        bt_lightcolor=view.findViewById(R.id.bt_lightvolor);
        bt_lightcolor.setOnClickListener(new BTlistener());


        return view;
    }
    public class BTlistener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_darkcolor:
                    break;
                case R.id.bt_lightvolor:
                    break;
                case R.id.bt_back:
                    getParentFragmentManager().popBackStack();
                    break;
                default:
                    break;
            }

        }
    }
}