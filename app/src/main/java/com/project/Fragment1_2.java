package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment1_2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_2, container, false);

        Button buttonInside3 = view.findViewById(R.id.buttonInside3);
        Button buttonOutside2 = view.findViewById(R.id.buttonOutside2);

        buttonInside3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Fragment1_3());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonOutside2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Fragment1_1());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button buttonKidney = view.findViewById(R.id.buttonKidney);
        Button buttonLiver = view.findViewById(R.id.buttonLiver);
        Button buttonDigestive = view.findViewById(R.id.buttonDigestive);

        buttonKidney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Organ("kidney"));
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

        return view;
    }
}
