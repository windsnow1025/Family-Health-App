package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment1 extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);

        Button buttonBrain = view.findViewById(R.id.buttonBrain);
        Button buttonHeart = view.findViewById(R.id.buttonHeart);
        Button buttonLung = view.findViewById(R.id.buttonLung);
        Button buttonLiver = view.findViewById(R.id.buttonLiver);
        Button buttonKidney = view.findViewById(R.id.buttonKidney);
        Button buttonStomach = view.findViewById(R.id.buttonStomach);
        Button buttonIntestine = view.findViewById(R.id.buttonIntestine);

        buttonBrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("brain"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        buttonHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("heart"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        buttonLung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("lung"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        buttonLiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("liver"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        buttonKidney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("kidney"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        buttonStomach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("stomach"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        buttonIntestine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new MedicalCases("intestine"));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
