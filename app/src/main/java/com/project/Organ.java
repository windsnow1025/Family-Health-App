package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Organ extends Fragment {

    View view;
    String organ;

    public Organ(String organ) {
        this.organ = organ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.organ, container, false);

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FragmentMain());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // WebView
        WebView webView = view.findViewById(R.id.webView);
        webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String url = "https://webview.windsnow1025.com/index.html?organ=" + organ;
        webView.loadUrl(url);

        // ViewPager2
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>(Arrays.asList(
                new FragmentReport(organ),
                new FragmentRecord(organ)
        ));

        ImageButton nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextItem = viewPager.getCurrentItem() + 1;
                viewPager.setCurrentItem(nextItem, true);
            }
        });

        ImageButton prevButton = view.findViewById(R.id.prev_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevItem = viewPager.getCurrentItem() - 1;
                viewPager.setCurrentItem(prevItem, true);
            }
        });

        PagerAdapter adapter = new PagerAdapter(getActivity(), fragments);
        viewPager.setAdapter(adapter);
        return view;
    }
}
