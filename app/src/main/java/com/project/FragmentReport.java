package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentReport extends Fragment {

    View view;
    String organ;
    public FragmentReport(String organ) {
        this.organ = organ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"时间", "部位", "症状"});
        data.add(new String[]{"Row 2, Column 1", "Row 2, Column 2", "Row 2, Column 3"});
        data.add(new String[]{"Row 3, Column 1", "Row 3, Column 2", "Row 3, Column 3"});

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TableAdapter(data));

        Button buttonEnterReport = view.findViewById(R.id.buttonEnterReport);
        buttonEnterReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new EnterReport(organ));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
