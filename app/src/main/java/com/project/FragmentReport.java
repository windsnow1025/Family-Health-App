package com.project;

import android.os.Bundle;
import android.util.Log;
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
import java.util.concurrent.TimeoutException;

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
            ArrayList<Report> reports;
            try {
                reports = reportDao.getReportList(username);
                Log.i("test", "从服务器获取体检报告");
            } catch (TimeoutException e) {
                Log.i("test", "超时，从本地获取体检报告");
                reports = userLocalDao.getReportList(username);
            }

            // Set report list to recycler view
            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"时间", "部位", "类型"});
            for (Report report : reports) {
                data.add(new String[]{report.getReport_date(), report.getReport_place(), report.getReport_type()});
            }

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ArrayList<Report> finalReports = reports;
            recyclerView.setAdapter(new TableAdapterEnter(data, new TableAdapterEnter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    // Delete report from database
                    try {
                        // Get report id
                        Integer report_id = finalReports.get(position - 1).getReport_No();

                        // Delete report
                        ReportDao reportDao = new ReportDao();
                        reportDao.deleteReport(username, report_id);

                        Log.i("test", "从服务器删除体检报告");
                    } catch (Exception e) {
                        Log.i("test", "从本地删除体检报告");
                        userLocalDao.deleteReport(username, finalReports.get(position - 1).getReport_No());
                    }
                    // Refresh fragment
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new Organ(organ));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }));

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
