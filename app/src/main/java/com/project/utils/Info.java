package com.project.utils;

public class Info {
    private String title;
    private String time;
    private String date;
    private int n,num_alert;

    private Boolean report;//用于标记提醒类型，true为体检,需要吃药，false为就诊，需要复检



    public Info(String mtitle, String mdate,String mtime,Boolean mflag ,int n,int num_alert) {
        this.title = mtitle;
        this.time = mtime;
        this.report = mflag;
        this.date = mdate;
        this.num_alert=num_alert;
        this.n=n;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFlag(Boolean flag) {
        this.report = flag;
    }

    public String getTime() {
        return time;
    }

    public int getN() {
        return n;
    }

    public int getNum_alert() {
        return num_alert;
    }

    public Boolean getFlag() {
        return report;
    }
}