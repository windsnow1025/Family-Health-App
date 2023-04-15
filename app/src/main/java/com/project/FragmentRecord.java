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

import com.project.JDBC.HistoryDao;
import com.project.Pojo.History;
import com.project.Sqlite.UserLocalDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

// 就诊记录显示
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

        // Get history list from database
        try {
            // Get username
            UserLocalDao userLocalDao = new UserLocalDao(getContext());
            userLocalDao.open();
            String username = userLocalDao.getUser();

            // Get history list
            HistoryDao historyDao = new HistoryDao();
            ArrayList<History> histories = null;
            try {
                histories=historyDao.getHistoryList(username);
            }
            catch (TimeoutException e)
            {
                Log.i("test","超时");
                userLocalDao.getReportList(username);
            }
            finally {
                Log.i("test","test");
            }
            // Set history list to recycler view
            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"时间", "医院", "类型"});
            for (History history : histories) {
                data.add(new String[]{history.getHistory_date(), history.getHistory_place(), history.getHistory_organ()});
            }

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new TableAdapter(data, new TableAdapter.OnItemClickListener() {
                @Override
                public void onClick(int pos) {
                    // TODO
                }
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }

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
