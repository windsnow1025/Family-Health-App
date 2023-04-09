package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentMain_2 extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_2, container, false);

        // Button Page
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutButtonPage, new ButtonPage());
        transaction.addToBackStack(null);
        transaction.commit();

        Button buttonInside3 = view.findViewById(R.id.buttonInside3);
        Button buttonOutside2 = view.findViewById(R.id.buttonOutside2);

        buttonInside3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_3());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonOutside2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_1());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button buttonLiver = view.findViewById(R.id.buttonLiver);
        Button buttonDigestive = view.findViewById(R.id.buttonDigestive);

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

        return view;
    }
}
