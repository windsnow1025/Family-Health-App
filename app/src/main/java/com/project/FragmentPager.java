package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentPager extends Fragment {
    public FragmentPager() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_record, container, false);

        // ViewPager2
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>(Arrays.asList(
                new FragmentReport(),
                new FragmentRecord()
        ));

        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        return view;
    }
}
