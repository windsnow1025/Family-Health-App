package com.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class diseaseFragment extends Fragment {

    private Button bt_back;
    private TextView bt_tnb;
    private TextView bt_gxy;
    private TextView bt_xzb;
    private Button bt_search;
    private EditText et_search;

    private Intent intent;
    public diseaseFragment() {
    }

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.disease, container, false);

        bt_back = view.findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new BTlistener());
        bt_search = view.findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new BTlistener());
        bt_tnb = view.findViewById(R.id.bt_tnb);
        bt_tnb.setOnClickListener(new BTlistener());
        bt_gxy = view.findViewById(R.id.bt_gxy);
        bt_gxy.setOnClickListener(new BTlistener());
        bt_xzb = view.findViewById(R.id.bt_xzb);
        bt_xzb.setOnClickListener(new BTlistener());
        et_search = view.findViewById(R.id.et_search);
        intent = new Intent(getContext(), BrowserActivity.class);
        return view;
    }

    public class BTlistener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_tnb:
                    intent.putExtra("disease", "糖尿病");
                    startActivity(intent);
                    break;
                case R.id.bt_xzb:
                    intent.putExtra("disease", "心脏病");
                    startActivity(intent);
                    break;
                case R.id.bt_gxy:
                    intent.putExtra("disease", "高血压");
                    startActivity(intent);
                    break;
                case R.id.bt_search:
                    intent.putExtra("disease",et_search.getText().toString());
                    startActivity(intent);
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