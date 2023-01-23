package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        WebView webView = view.findViewById(R.id.WebView);
        webView.loadUrl("https://www.windsnow1025.com/webview/3d.html");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSBridge(this), "JSBridge");
        return view;
    }
    public void getMessage(String message) {
        TextView textView = view.findViewById(R.id.textViewMessage);
        textView.setText(message);
    }
}
