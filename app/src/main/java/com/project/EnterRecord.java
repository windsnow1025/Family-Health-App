package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

// 就诊记录录入
public class EnterRecord extends Fragment {

    String organ;
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

        EditText editTextDate = view.findViewById(R.id.editTextDate);
        EditText editTextHospital = view.findViewById(R.id.editTextHospital);
        EditText editTextType = view.findViewById(R.id.editTextType);
        EditText editTextOrgan = view.findViewById(R.id.editTextOrgan);
        EditText editTextSymptom = view.findViewById(R.id.editTextSymptom);
        EditText editTextConclusion = view.findViewById(R.id.editTextConclusion);
        EditText editTextSuggestion = view.findViewById(R.id.editTextSuggestion);

        editTextOrgan.setText(organ);

        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = editTextDate.getText().toString();
                String hospital = editTextHospital.getText().toString();
                String type = editTextType.getText().toString();
                String organ = editTextOrgan.getText().toString();
                String symptom = editTextSymptom.getText().toString();
                String conclusion = editTextConclusion.getText().toString();
                String suggestion = editTextSuggestion.getText().toString();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ(organ));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
