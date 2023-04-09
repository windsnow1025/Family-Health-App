package com.project;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.JDBC.ReportDao;
import com.project.Pojo.Report;
import com.project.Sqlite.UserLocalDao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// 体检报告录入
public class EnterReport extends Fragment {

    String organ;

    String username;

    String date;
    String hospital;
    String type;

    EditText editTextDate;
    EditText editTextHospital;
    EditText editTextType;

    Bitmap bitmap;

    public EnterReport(String organ) {
        this.organ = organ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_report, container, false);


        try {
            UserLocalDao userLocalDao = new UserLocalDao(getContext());
            userLocalDao.open();
            username = userLocalDao.getUser();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Set an OnClickListener on the button to launch the gallery
        Button buttonUpload = view.findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(v -> galleryLauncher.launch("image/*"));

        // Get views
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextHospital = view.findViewById(R.id.editTextHospital);
        editTextType = view.findViewById(R.id.editTextType);

        // Confirm button
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = editTextDate.getText().toString();
                hospital = editTextHospital.getText().toString();
                type = editTextType.getText().toString();

                // Turn bitmap into string
                if (bitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    String bitmapString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }

                // Upload to database
                ArrayList<Report> reportList=new ArrayList<>();
                String account="1111";
                Log.i("主线程","多线程数据库测试开始");
                FutureTask<ArrayList<Report>> futureTask=new FutureTask<>(()->{                     //使用FutureTask创建可获得返回值的执行任务 泛型为返回值类型
                        Thread.sleep(2000);                                                   //使用sleep模拟耗时操作
                        ReportDao reportDao=new ReportDao();
                        Log.i("子线程","准备调用数据库");
                        reportDao.getConnection();                                                  //调用mysql记得连接
//                        Report report = new Report();
//                        reportDao.getConnection();
//                        report.setPhone_number("11111");
//                        report.setReport_content("11111");
//                        report.setReport_No(0);
//                        report.setReport_type("1111");
//                        report.setReport_place("!111");
//                        report.setReport_date("2022-07-08");
//                        report.setIs_deleted("false");
//                        reportDao.insertReport("1111",report);
                        ArrayList<Report> reportArrayList=reportDao.getReportList(account);
                        reportDao.closeConnection();                                                //关闭连接 我不确定不关闭会不会产生bug
                        Log.i("子线程","数据库调用结束,返回数据");
                        return reportArrayList;
                });
                new Thread(futureTask).start();                                                     //必须使用start,使用run会导致异常
                try {
                    reportList=futureTask.get();                                                    //使用get方法获得返回值 需要异常处理
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Log.i("主线程","数据库中Report数量为"+reportList.size());
                Log.i("主线程","多线程数据库测试结束");
//                new Thread(()->{
//                    UserLocalDao userLocalDao=new UserLocalDao(getActivity().getApplicationContext());
//                    userLocalDao.open();
//                    System.out.println(userLocalDao.getHistoryList("1111").size());
//                    ReportDao reportDao=new ReportDao();
//                    reportDao.getConnection();
//                    System.out.println(reportDao.getReportList("1111").size());
//                    Log.i("test","准备上传");
//                    Report report = new Report();
//                    reportDao.getConnection();
//                    report.setPhone_number("11111");
//                    report.setReport_content("11111");
//                    report.setReport_No(0);
//                    report.setReport_type("1111");
//                    report.setReport_place("!111");
//                    report.setReport_date("2022-07-08");
//                    report.setIs_deleted("false");
//                    reportDao.insertReport("11111",report);
//                    Log.i("test","结束上传");
//
//
//                }).start();

                // Jump to 3d model
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ(organ));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
}
