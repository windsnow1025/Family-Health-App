package com.project;

import android.webkit.JavascriptInterface;

import androidx.fragment.app.Fragment;

public class JSBridge {
    Fragment fragment;
    public JSBridge(Fragment fragment) {
        this.fragment = fragment;
    }
//    @JavascriptInterface
//    public void showMessageInNative(String message) {
//        ((Fragment1_3d) fragment).getMessage(message);
//    }
}
