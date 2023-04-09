package com.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.utils.Info;
import com.project.utils.InfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlert extends Fragment {

    private static List<Info> infoList=new ArrayList<>();

    public List<Info> getInfoList(List<Info> list,Boolean flag){

        if(flag){
            list.add(new Info("","","",true));
        }
        return  list;
    }
    InfoAdapter adapter;
    ListView listView;
    TableLayout tableLayout;
    Boolean flag=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


//        infoList=getInfoList(infoList,flag);
        View view = inflater.inflate(R.layout.fragment_alert, container, false);
        listView= (ListView) view.findViewById(R.id.list_view);
        tableLayout=view.findViewById(R.id.tableLayout);
        if(infoList.isEmpty()){
            tableLayout.setVisibility(View.GONE);
            view.findViewById(R.id.tv_blank).setVisibility(View.VISIBLE);
        }
        else {
            tableLayout.setVisibility(View.VISIBLE);
            view.findViewById(R.id.tv_blank).setVisibility(View.GONE);
        }
        adapter=new InfoAdapter(requireContext(),R.layout.listview,infoList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("删除该提醒？");
                builder.setMessage("请问您确定要删除该提醒吗？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        infoList.remove(position);
                        Fragment fragment = new FragmentAlert();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        Toast.makeText(getContext(), "提醒删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return false;
            }
        });
        Button button = view.findViewById(R.id.btn_add_dada);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentAlert_set(adapter,listView));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}
