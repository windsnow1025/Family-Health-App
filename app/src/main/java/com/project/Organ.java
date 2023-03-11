package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class Organ extends Fragment {

    View view;
    String organ;

    public Organ(String organ) {
        this.organ = organ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.organ, container, false);

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        ImageView imageOrgan = view.findViewById(R.id.imageOrgan);
        switch (organ) {
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
