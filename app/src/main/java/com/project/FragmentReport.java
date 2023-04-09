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

import com.project.JDBC.ReportDao;
import com.project.JDBC.UserDao;
import com.project.Pojo.Report;
import com.project.Pojo.UserInfo;
import com.project.Sqlite.UserLocalDao;

import java.util.ArrayList;
import java.util.List;

// 体检报告显示
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

        // Get report list from database
        try {
            // Get username
            UserLocalDao userLocalDao = new UserLocalDao(getContext());
            userLocalDao.open();
            String username = userLocalDao.getUser();

            // Get report list
            ReportDao reportDao = new ReportDao();
            ArrayList<Report> reports =userLocalDao.getReportList(username);

            // Set report list to recycler view
            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"时间", "部位", "症状"});
            for (Report report : reports) {
                data.add(new String[]{report.getReport_date(), report.getReport_place(), report.getReport_content()});
            }

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new TableAdapter(data));

        } catch (Exception e) {
            e.printStackTrace();
        }

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
