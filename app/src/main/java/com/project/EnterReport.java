package com.project;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.project.JDBC.UserDao;
import com.project.Pojo.Report;
import com.project.Sqlite.UserLocalDao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

        ReportDao reportDao = new ReportDao();

        try {
            UserLocalDao userLocalDao = new UserLocalDao(getContext());
            username = userLocalDao.getUser();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Set an OnClickListener on the button to launch the gallery
        Button buttonUpload = view.findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(v -> galleryLauncher.launch("image/*"));

        editTextDate = view.findViewById(R.id.editTextDate);
        editTextHospital = view.findViewById(R.id.editTextHospital);
        editTextType = view.findViewById(R.id.editTextType);

        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = editTextDate.getText().toString();
                hospital = editTextHospital.getText().toString();
                type = editTextType.getText().toString();

                // Turn bitmap into string
                if (bitmap != null) {
                    return;
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String bitmapString = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Report report = new Report();
                report.setPhone_number(username);
                report.setReport_content(bitmapString);
                report.setReport_No(0); // 由于是插入新数据，所以report_No设为0
                report.setReport_type(type);
                report.setReport_place(hospital);
                report.setReport_date(date);
                report.setIs_deleted("false");
                Boolean success = reportDao.insertReport(username, report);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ_3d(organ));
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
