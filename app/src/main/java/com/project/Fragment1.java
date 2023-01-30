package com.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);

        WebView webView = view.findViewById(R.id.WebView);
        webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSBridge(this), "JSBridge");
        webView.loadUrl("https://www.windsnow1025.com/webview/3d.html");
        return view;
    }
    public void getMessage(String message) {
        TextView textView = view.findViewById(R.id.textViewMessage);
        textView.setText(message);
    }
}
