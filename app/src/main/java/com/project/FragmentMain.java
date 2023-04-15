package com.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.JDBC.UserDao;
import com.project.Pojo.UserInfo;
import com.project.Sqlite.UserLocalDao;

import java.util.concurrent.TimeoutException;

public class FragmentMain extends Fragment {
    View view;
    private LeftNavigation leftNavigation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        UserLocalDao userLocalDao = new UserLocalDao(getActivity().getApplicationContext());
        userLocalDao.open();
        String username = userLocalDao.getUser();
        // Get Sex
        String gender = "male";
        try {
            UserInfo userInfo = new UserDao().getUserInformation(username);
            gender = userInfo.getSex();
            // To be fixed
        }catch (TimeoutException e){
            gender=userLocalDao.gerUserInfo(username).getSex();
            Log.i("网络错误","超时");
        }

        // Set Image
        ImageView imageAnatomy = view.findViewById(R.id.imageAnatomy);
        imageAnatomy.scrollBy(0, 0);
        if (gender.equals("female")) {
            imageAnatomy.setImageResource(R.drawable.female);
        } else {
            imageAnatomy.setImageResource(R.drawable.male);
        }


        Button buttonInside = view.findViewById(R.id.buttonInside);

        buttonInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain_1());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Left Navigation
        leftNavigation = new LeftNavigation();
        FragmentTransaction transactionLeft = getParentFragmentManager().beginTransaction();
        transactionLeft.add(R.id.layoutLeftNavigation, leftNavigation);
        transactionLeft.commit();

        // Button Navigation
        Button buttonNavigation = view.findViewById(R.id.buttonNavigation);
        buttonNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftNavigation.isVisible()) {
                    leftNavigation.getView().setVisibility(View.GONE);
                } else {
                    leftNavigation.getView().setVisibility(View.VISIBLE);
                }
            }
        });

        // Button Page
        FragmentTransaction transactionButton = getParentFragmentManager().beginTransaction();
        transactionButton.replace(R.id.frameLayoutButtonPage, new ButtonPage());
        transactionButton.addToBackStack(null);
        transactionButton.commit();

        return view;
    }
}
