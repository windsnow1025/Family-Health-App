package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LeftNavigation extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.left_navigation, container, false);

        Button buttonBrain = view.findViewById(R.id.buttonBrain);
        Button buttonRespiratory = view.findViewById(R.id.buttonRespiratory);
        Button buttonRenal = view.findViewById(R.id.buttonRenal);
        Button buttonLiver = view.findViewById(R.id.buttonLiver);
        Button buttonDigestive = view.findViewById(R.id.buttonDigestive);
        Button buttonMusculoskeletal = view.findViewById(R.id.buttonMusculoskeletal);
        Button buttonCardiovascular = view.findViewById(R.id.buttonCardiovascular);

        Button buttonPage1 = view.findViewById(R.id.buttonPage1);
        Button buttonPage2 = view.findViewById(R.id.buttonPage2);
        Button buttonPage3 = view.findViewById(R.id.buttonPage3);

        buttonBrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("brain"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonRespiratory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("respiratory"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonRenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("renal"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonLiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("liver"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonDigestive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("digestive"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonMusculoskeletal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("musculoskeletal"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonCardiovascular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("cardiovascular"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_1());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_2());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonPage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_3());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
