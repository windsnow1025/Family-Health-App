package com.project;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.project.utils.Info;
import com.project.utils.InfoAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class FragmentAlert_set extends Fragment {

    private View view;
    private TableAdapter tableAdapter1;
    private TableAdapter tableAdapter2;
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
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"时间", "医院", "类型"});
        data.add(new String[]{"2021-2", "1.2", "体检6"});
        data.add(new String[]{"2", "2", "体检7"});

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tableAdapter1=new TableAdapter(data,new TableAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentDetails_Record(data.get(pos),adapter));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        recyclerView.setAdapter(tableAdapter1);

        List<String[]> data1 = new ArrayList<>();
        data1.add(new String[]{"时间", "部位", "症状"});
        data1.add(new String[]{"2021-2-2", "大腿", "好好休息一下"});
        data1.add(new String[]{"R", "C", "3"});

        RecyclerView recyclerView1 = view.findViewById(R.id.recycler_view1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        tableAdapter2=new TableAdapter(data1,new TableAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentDetails(data1.get(pos),adapter));
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
        recyclerView1.setAdapter(tableAdapter2);


        return view;

    }


}