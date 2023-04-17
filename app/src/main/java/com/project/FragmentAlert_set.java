package com.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.JDBC.HistoryDao;
import com.project.JDBC.ReportDao;
import com.project.Pojo.History;
import com.project.Pojo.Report;
import com.project.Sqlite.UserLocalDao;
import com.project.utils.InfoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class FragmentAlert_set extends Fragment {

    private View view;
    private TableAdapter tableAdapter1;
    private TableAdapter tableAdapter2;
    private  ArrayList<Report> reportArrayList;
    private  ArrayList<History> historyArrayList;
    private UserLocalDao userLocalDao;
    private ReportDao reportDao;
    private HistoryDao historyDao;
    private String userID;
    InfoAdapter adapter;
    ListView listView;
    public FragmentAlert_set(InfoAdapter madapter,ListView mlistView) {
        this.adapter=madapter;
        this.listView=mlistView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_alert_set, container, false);
        userLocalDao = new UserLocalDao(getActivity().getApplicationContext());
        userLocalDao.open();
        userID=userLocalDao.getUser();
        reportDao=new ReportDao();
        historyDao=new HistoryDao();
        try {
            reportArrayList=reportDao.getReportList(userID);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        try {
            historyArrayList=historyDao.getHistoryList(userID);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"时间", "医院", "类型"});
        for (Report report:reportArrayList
             ) {//时间、地点、类型、编号
            data.add(new String[]{report.getReport_date(), report.getReport_place(), report.getReport_type(),report.getReport_No().toString()});
        }

        List<String[]> data1 = new ArrayList<>();
        data1.add(new String[]{"时间", "医院", "部位"});
        for (History history :
                historyArrayList) {
            data1.add(new String[]{history.getHistory_date(),history.getHistory_place(),history.getHistory_organ(), String.valueOf(history.getHistory_No())});
        }



        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tableAdapter1=new TableAdapter(data,new TableAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("类型选择");
                builder.setMessage("请选择为该记录添加提醒的类型");
                builder.setNegativeButton("吃药提醒", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        int i= Integer.parseInt(data.get(pos)[3]);
                        transaction.replace(R.id.fragment_container, new FragmentDetails(i,true,adapter));
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                builder.setPositiveButton("复诊提醒", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        int i= Integer.parseInt(data.get(pos)[3]);
                        transaction.replace(R.id.fragment_container, new FragmentDetails_Record(i,true,adapter));
                        transaction.addToBackStack(null);
                        transaction.commit();*/
                    }
                });
                builder.show();
            }
        });

        recyclerView.setAdapter(tableAdapter1);


        RecyclerView recyclerView1 = view.findViewById(R.id.recycler_view1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        tableAdapter2=new TableAdapter(data1,new TableAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("类型选择");
                builder.setMessage("请选择为该记录添加提醒的类型");
                builder.setNegativeButton("吃药提醒", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        int i= Integer.parseInt(data1.get(pos)[3]);
                        transaction.replace(R.id.fragment_container, new FragmentDetails(i,false,adapter));
                        transaction.addToBackStack(null);
                        transaction.commit();*/
                    }
                });
                builder.setPositiveButton("复诊提醒", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        int i= Integer.parseInt(data1.get(pos)[3]);
                        transaction.replace(R.id.fragment_container, new FragmentDetails_Record(i,false,adapter));
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                builder.show();

            }

        });
        recyclerView1.setAdapter(tableAdapter2);


        return view;

    }


}