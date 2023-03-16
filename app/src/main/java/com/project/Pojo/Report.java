package com.project.Pojo;

import java.io.Serializable;

public class Report implements Serializable {
    private Integer report_No;
    private String phone_number;
    private String report_content;
    private String report_type;
    private String report_place;
    private String report_date;
    private String is_deleted;

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Integer getReport_No() {
        return report_No;
    }

    public void setReport_No(Integer report_No) {
        this.report_No = report_No;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getReport_content() {
        return report_content;
    }

    public void setReport_content(String report_content) {
        this.report_content = report_content;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getReport_place() {
        return report_place;
    }

    public void setReport_place(String report_place) {
        this.report_place = report_place;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }
}
