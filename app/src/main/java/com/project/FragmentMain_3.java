package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentMain_3 extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1_3, container, false);

        // Button Page
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutButtonPage, new ButtonPage());
        transaction.addToBackStack(null);
        transaction.commit();

        Button buttonOutside3 = view.findViewById(R.id.buttonOutside3);

        buttonOutside3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_2());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button buttonCardiovascular = view.findViewById(R.id.buttonCardiovascular);
        Button buttonMusculoskeletal = view.findViewById(R.id.buttonMusculoskeletal);

        buttonCardiovascular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("cardiovascular"));
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

        return view;
    }
}
