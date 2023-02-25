package com.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentHome extends Fragment {
    private ImageButton imageButton;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton bt_disease;
    private ImageButton bt_kefu;
    private TextView tv_user;


    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);

        /*个人用户登录*/
        tv_user=view.findViewById(R.id.textViewLoginSignup);
        /*若用户已登录则让按钮失效*/
        if(true){
            tv_user.setEnabled(false);
            tv_user.setText("username");
        }
        tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login_1.class);
                startActivity(intent);
            }
        });



        /*个人中心*/
        imageButton = view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new personalCenter());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        /*数据同步*/
        imageButton1 = view.findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    Toast.makeText(getContext(), "数据已实时同步成功", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*切换账号*/
        imageButton2 = view.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("切换用户？");
                builder.setMessage("请问您确定要退出当前登录吗？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), Login_1.class);
                        startActivity(intent);
                    }
                });
                builder.show();

            }
        });

        /*设置*/
        imageButton3 = view.findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction3 = getParentFragmentManager().beginTransaction();
                transaction3.replace(R.id.fragment_container, new settingFragment());
                transaction3.addToBackStack(null);
                transaction3.commit();
            }
        });

        /*常见疾病*/
        bt_disease = view.findViewById(R.id.bt_disease);
        bt_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction3 = getParentFragmentManager().beginTransaction();
                transaction3.replace(R.id.fragment_container, new diseaseFragment());
                transaction3.addToBackStack(null);
                transaction3.commit();
            }
        });

        /*联系我们*/
        bt_kefu = view.findViewById(R.id.bt_kefu);
        bt_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction3 = getParentFragmentManager().beginTransaction();
                transaction3.replace(R.id.fragment_container, new diseaseFragment());
                transaction3.addToBackStack(null);
                transaction3.commit();
            }
        });
        return view;
    }


}
