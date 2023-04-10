package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.JDBC.HistoryDao;
import com.project.Pojo.History;
import com.project.Sqlite.UserLocalDao;

public class EnterRecord extends Fragment {

    String organ;

    String username;

    String date;
    String hospital;
    String type;
    String symptom;
    String conclusion;
    String suggestion;

    EditText editTextDate;
    EditText editTextHospital;
    EditText editTextType;
    EditText editTextOrgan;
    EditText editTextSymptom;
    EditText editTextConclusion;
    EditText editTextSuggestion;

    public EnterRecord(String organ) {
        this.organ = organ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_record, container, false);

        // Get username
        try {
            UserLocalDao userLocalDao = new UserLocalDao(getContext());
            userLocalDao.open();
            username = userLocalDao.getUser();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Get views
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextHospital = view.findViewById(R.id.editTextHospital);
        editTextType = view.findViewById(R.id.editTextType);
        editTextOrgan = view.findViewById(R.id.editTextOrgan);
        editTextSymptom = view.findViewById(R.id.editTextSymptom);
        editTextConclusion = view.findViewById(R.id.editTextConclusion);
        editTextSuggestion = view.findViewById(R.id.editTextSuggestion);

        // Set default values
        editTextOrgan.setText(organ);

        // Confirm button
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data
                date = editTextDate.getText().toString();
                hospital = editTextHospital.getText().toString();
                type = editTextType.getText().toString();
                organ = editTextOrgan.getText().toString();
                symptom = editTextSymptom.getText().toString();
                conclusion = editTextConclusion.getText().toString();
                suggestion = editTextSuggestion.getText().toString();

                // Insert data into database
                try {
                    HistoryDao historyDao = new HistoryDao();
                    historyDao.getConnection();
                    History history = new History();
                    history.setHistory_date(date);
                    history.setHistory_place(hospital);
                    history.setHistory_organ(type);
                    history.setHistory_organ(organ);
                    history.setSymptom(symptom);
                    history.setConclusion(conclusion);
                    history.setSuggestion(suggestion);
                    historyDao.insertHistory(username, history);
                    historyDao.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Jump to organ page
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ(organ));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}