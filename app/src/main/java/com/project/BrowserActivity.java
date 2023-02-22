package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BrowserActivity extends AppCompatActivity {

    private WebView wv_browser;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        intent = getIntent();
        String searchText = intent.getStringExtra("disease");
        wv_browser = findViewById(R.id.wv_browser);
        //设置 WebView 属性，能够执行 Javascript 脚本
        wv_browser.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        Uri uri;
        try {
            if (searchText.equals("")) {
                uri = Uri.parse("https://www.baidu.com");
            } else {
                uri = Uri.parse("https://www.baidu.com/s?&ie=utf-8&oe=UTF-8&wd=" + URLEncoder.encode(searchText, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        wv_browser.loadUrl(uri.toString());
        wv_browser.setWebViewClient(new WebViewClientDemo());
    }

    public class WebViewClientDemo extends WebViewClient {
        // 在 WebView 中而不是默认浏览器中显示页面
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                view.loadUrl(url);
                return false;
            } else {
                // 如果不是http开头的地址，就是走这里。看是否需要打开新的意图程序
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    BrowserActivity.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
    }
}
