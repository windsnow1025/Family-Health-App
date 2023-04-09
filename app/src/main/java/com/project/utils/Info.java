package com.project.utils;

public class Info {
    private String title;
    private String time;
    private String date;
    private Boolean flag;



    public Info(String mtitle, String mdate,String mtime,Boolean mflag) {
        this.title = mtitle;
        this.time = mtime;
        this.flag = mflag;
        this.date = mdate;
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
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public Boolean getFlag() {
        return flag;
    }
}