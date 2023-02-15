package com.project.utils;

import android.app.Application;

import java.util.HashMap;

public class MainApplication  extends Application {
    private static MainApplication mainApplication;

    public HashMap<String,String> map=new HashMap<String,String>();
    public static MainApplication getInstance(){
        return mainApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication=this;
    }
}
