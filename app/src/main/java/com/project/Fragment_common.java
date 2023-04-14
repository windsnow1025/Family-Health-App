package com.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Fragment_common extends Fragment {

    private final int tag;
    private Button back;
    private TextView notice;
    private TextView update;
    private TextView about;
    private TextView chat;



    public Fragment_common(int i) {
       this.tag=i;
    }

    private void init(View view){
        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        notice=view.findViewById(R.id.notice);
        notice.setVisibility(View.GONE);
        update=view.findViewById(R.id.update);
        update.setVisibility(View.GONE);
        about=view.findViewById(R.id.about);
        about.setVisibility(View.GONE);
        chat=view.findViewById(R.id.chat);
        chat.setVisibility(View.GONE);
    }

    private void load(){
        switch (tag){
            case 1:notice.setVisibility(View.VISIBLE);
                break;
            case 2:update.setVisibility(View.VISIBLE);
                break;
            case 3:about.setVisibility(View.VISIBLE);
                break;
            case 4:chat.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common, container, false);
        init(view);
        load();
        return view;
    }
}