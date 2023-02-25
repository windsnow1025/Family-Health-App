package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ButtonPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.button_page, container, false);

        Button buttonPage1 = view.findViewById(R.id.buttonPage1);
        buttonPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_1());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button buttonPage2 = view.findViewById(R.id.buttonPage2);
        buttonPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_2());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button buttonPage3 = view.findViewById(R.id.buttonPage3);
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
