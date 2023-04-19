package com.project;

import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.JDBC.ReportDao;
import com.project.Pojo.Report;
import com.project.Sqlite.UserLocalDao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

import com.googlecode.tesseract.android.TessBaseAPI;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
    EditText editTextOCRTxt;

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

        // Get username
        try {
            UserLocalDao userLocalDao = new UserLocalDao(this.getActivity().getApplicationContext());
            userLocalDao.open();
            username = userLocalDao.getUser();                                                      //关于这里的getUser()是获取当前登录的账号 理论上应该需要从登录入口进入才能获取到 我测试的时候是事先写了数据到本地进行测试
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
        editTextOCRTxt = view.findViewById(R.id.editTextOCRTxt);

        // Set to open date picker when click on date EditText
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set date
                        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                        // Set date to EditText
                        editTextDate.setText(date);
                    }
                }, year, month, day);

                // Show DatePickerDialog
                datePickerDialog.show();
            }
        });

        // Confirm button
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data
                date = editTextDate.getText().toString();
                hospital = editTextHospital.getText().toString();
                type = editTextType.getText().toString();

                // Turn bitmap into string
                String bitmapString = null;
                if (bitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    bitmapString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }

                // Upload to database
                Boolean insertStatus = false;
                Log.i("主线程", "数据库测试开始");
                ReportDao reportDao = new ReportDao();
                Report report = new Report();
                report.setPhone_number(username);
                report.setPhone_number(username);
                report.setReport_content(bitmapString);
                report.setReport_type(type);
                report.setReport_place(hospital);
                if (date.equals("")) {
                    //判定日期是否填写 未填写则设置为null
                    report.setReport_date(null);
                } else {
                    report.setReport_date(date);
                }
                report.setIs_deleted("false");
                try {
                    insertStatus = reportDao.insertReport(username, report);
                } catch (TimeoutException e) {
                    Log.i("Test", "网络有问题");
                } finally {
                    Log.i("Log", "插入结束");
                }


                Log.i("主线程", "报告插入情况" + insertStatus);
                Log.i("主线程", "数据库测试结束");

                // Jump to organ page
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ(organ));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        // Get bitmap from uri
        bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set datapath
        String datapath = this.getActivity().getExternalFilesDir(null) + "/tesseract/";
        String datapath2 = this.getActivity().getExternalFilesDir(null) + "/tesseract/tessdata/";

        // Create directory
        File dir = new File(datapath);
        if (!dir.exists()) {
            Log.e("test", "数据路径1不存在");
            return;
        }

        // Create directory
        File dir2 = new File(datapath2);
        if (!dir2.exists()) {
            if (!dir2.mkdirs()) {
                Log.e("test", "创建路径2失败");
                return;
            }
        }

        // Path exists
        Log.i("test", "路径2存在");

        // Set language
        String language = "eng";

        // New thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Download trained data
                String filename = language + ".traineddata";
                File file = new File(dir2, filename);
                if (!file.exists()) {
                    try {
                        URL url = new URL("https://github.com/tesseract-ocr/tessdata/blob/main/eng.traineddata");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        OutputStream output = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = input.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                        output.flush();
                        output.close();
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    // Initialize Tesseract
                    TessBaseAPI tessBaseAPI = new TessBaseAPI();

                    // Check if datapath exists
                    if (!dir.exists()) {
                        Log.e("test", "数据路径不存在");
                        return;
                    }

                    tessBaseAPI.init(datapath, language);
                    tessBaseAPI.setImage(bitmap);
                    String ocrtxt = tessBaseAPI.getUTF8Text();
                    tessBaseAPI.end();

                    // Set OCR result to EditText
                    editTextOCRTxt.setText(ocrtxt);
                } catch (Exception e) {
                    Log.e("test", "OCR出错");
                    e.printStackTrace();
                }
            }
        }).start();
    });
}
