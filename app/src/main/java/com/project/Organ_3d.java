package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Organ_3d extends Fragment {

    View view;
    String organ;

    public Organ_3d(String organ) {
        this.organ = organ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.organ_3d, container, false);

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        // WebView
        WebView webView = view.findViewById(R.id.webView);
        webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String url = "https://www.windsnow1025.com/webview/3d.html?organ=" + organ;
        webView.loadUrl(url);

        // ViewPager2
        FragmentReportRecord reportRecordFragment = new FragmentReportRecord();
        getChildFragmentManager().beginTransaction().add(R.id.view_pager, reportRecordFragment).commit();

        return view;
    }
}
