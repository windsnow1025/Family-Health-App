package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentMain_1 extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1_1, container, false);

        // Button Page
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutButtonPage, new ButtonPage());
        transaction.addToBackStack(null);
        transaction.commit();

        Button buttonInside2 = view.findViewById(R.id.buttonInside2);
        Button buttonOutside = view.findViewById(R.id.buttonOutside);

        buttonInside2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_2());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button buttonBrain = view.findViewById(R.id.buttonBrain);
        Button buttonRespiratory = view.findViewById(R.id.buttonRespiratory);
        Button buttonUrinary = view.findViewById(R.id.buttonRenal);

        buttonBrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ_3d("brain"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonRespiratory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ_3d("respiratory"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        buttonUrinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ_3d("renal"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
