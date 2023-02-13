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
            case "lung":
                imageOrgan.setImageResource(R.drawable.lung);
                break;
            case "liver":
                imageOrgan.setImageResource(R.drawable.liver);
                break;
            case "kidney":
                imageOrgan.setImageResource(R.drawable.kidney);
                break;
            case "stomach":
                imageOrgan.setImageResource(R.drawable.stomach);
                break;
            case "intestine":
                imageOrgan.setImageResource(R.drawable.intestine);
                break;
        }

        return view;
    }

}
