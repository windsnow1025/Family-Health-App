package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment1_3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_3, container, false);

        Button buttonOutside3 = view.findViewById(R.id.buttonOutside3);

        buttonOutside3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Fragment1_2());
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
