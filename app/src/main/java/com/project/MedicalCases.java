package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class MedicalCases extends Fragment {

    View view;
    String organ;

    public MedicalCases(String organ) {
        this.organ = organ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medial_cases, container, false);

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        ImageView imageOrgan = view.findViewById(R.id.imageOrgan);
        switch (organ) {
            case "brain":
                imageOrgan.setImageResource(R.drawable.brain);
                break;
            case "heart":
                imageOrgan.setImageResource(R.drawable.heart);
                break;
            case "respiratory":
                imageOrgan.setImageResource(R.drawable.respiratory);
                break;
            case "liver":
                imageOrgan.setImageResource(R.drawable.liver);
                break;
            case "kidney":
                imageOrgan.setImageResource(R.drawable.kidney);
                break;
            case "digestive":
                imageOrgan.setImageResource(R.drawable.digestive);
                break;
            case "urinary":
                imageOrgan.setImageResource(R.drawable.urinary);
                break;
            case "musculoskeletal":
                imageOrgan.setImageResource(R.drawable.musculoskeletal);
                break;
            case "cardiovascular":
                imageOrgan.setImageResource(R.drawable.cardiovascular);
                break;
        }

        return view;
    }
}
