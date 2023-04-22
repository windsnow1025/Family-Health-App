package com.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class settingFragment extends Fragment {
    private static Button bt_back;
    private Button bt_darkcolor;
    private Button bt_lightcolor;
    private TextView tv_update;
    private TextView tv_notice;
    private TextView tv_about;
    private LinearLayout LL;


    public settingFragment() {
        // Required empty public constructor
    }

    public void init(View view) {
        bt_back = view.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new BTlistener());
        bt_darkcolor = view.findViewById(R.id.bt_darkcolor);
        bt_darkcolor.setOnClickListener(new BTlistener());
        bt_lightcolor = view.findViewById(R.id.bt_lightcolor);
        bt_lightcolor.setOnClickListener(new BTlistener());
        tv_notice=view.findViewById(R.id.tv_notice);
        tv_notice.setOnClickListener(new BTlistener());
        tv_update=view.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(new BTlistener());
        tv_about=view.findViewById(R.id.tv_about);
        tv_about.setOnClickListener(new BTlistener());
        LL=view.findViewById(R.id.LL);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        init(view);

        return view;
    }

    private class BTlistener implements View.OnClickListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            switch (v.getId()) {
                case R.id.bt_darkcolor:
                    LL.setBackgroundColor(getResources().getColor(R.color.dark));
                    break;
                case R.id.bt_lightcolor:
                    LL.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case R.id.bt_back:
                    getParentFragmentManager().popBackStack();
                    break;
                case R.id.tv_notice:
                    transaction.replace(R.id.fragment_container, new Fragment_common(1));
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.tv_update:
                    transaction.replace(R.id.fragment_container, new Fragment_common(2));
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.tv_about:
                    transaction.replace(R.id.fragment_container, new Fragment_common(3));
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                default:
                    break;
            }

        }
    }
}