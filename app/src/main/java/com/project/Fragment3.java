package com.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment3 extends Fragment {
  private   ImageButton imageButton;
  private   ImageButton imageButton1;
  private   ImageButton imageButton2;
  private   ImageButton imageButton3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        TextView textView = view.findViewById(R.id.textViewLoginSignup);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Login_1.class);
                startActivity(intent);
            }
        });

        imageButton=view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new ButtonListener());

        imageButton1=view.findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new ButtonListener());

        imageButton2=view.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new ButtonListener());

        imageButton3=view.findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new ButtonListener());
        return view;
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton:
                    break;
                case R.id.imageButton1:
                    System.out.println("1");
                    break;
                case R.id.imageButton2:
                    System.out.println("2");
                    break;
                case R.id.imageButton3:
                    System.out.println("3");
                    break;
                default:
                    break;
            }
        }
    }
}
