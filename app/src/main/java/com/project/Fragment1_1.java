package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment1_1 extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1_1, container, false);

        Button buttonInside2 = view.findViewById(R.id.buttonInside2);
        Button buttonOutside = view.findViewById(R.id.buttonOutside);

        buttonInside2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Fragment1_2());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Fragment1());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button buttonBrain = view.findViewById(R.id.buttonBrain);
        Button buttonRespiratory = view.findViewById(R.id.buttonRespiratory);
        Button buttonUrinary = view.findViewById(R.id.buttonUrinary);

        buttonBrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("brain"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonRespiratory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("respiratory"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        buttonUrinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("urinary"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}