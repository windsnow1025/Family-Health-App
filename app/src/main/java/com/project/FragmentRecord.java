package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// 病例记录显示
public class FragmentRecord extends Fragment {

    View view;
    String organ;
    public FragmentRecord(String organ) {
        this.organ = organ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_record, container, false);

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"时间", "医院", "类型"});
        data.add(new String[]{"Row 2, Column 1", "Row 2, Column 2", "Row 2, Column 3"});
        data.add(new String[]{"Row 3, Column 1", "Row 3, Column 2", "Row 3, Column 3"});

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TableAdapter(data));

        Button buttonEnterRecord = view.findViewById(R.id.buttonEnterRecord);
        buttonEnterRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new EnterRecord(organ));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
