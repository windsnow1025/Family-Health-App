package com.project.Pojo;

public class Alert {
    private Integer Alert_No;
    private String phone_number;
    private String date;
    private String cycle;
    private String content;
    private String is_medicine;
    private String type;
    private Integer type_No;
//    private String is_deleted;

    public Alert() {
    }

    public Alert(Integer alert_No, String is_medicine, String phone_number, String date, String cycle, String content, String type, Integer type_No, String is_deleted) {
        Alert_No = alert_No;
        this.phone_number = phone_number;
        this.is_medicine=is_medicine;
        this.date = date;
        this.cycle = cycle;
        this.content = content;
        this.type = type;
        this.type_No = type_No;
//        this.is_deleted = is_deleted;
    }
//    public String getIs_deleted() {
//        return is_deleted;
//    }

    public String getIs_medicine() {
        return is_medicine;
    }

    public void setIs_medicine(String is_medicine) {
        this.is_medicine = is_medicine;
    }

//    public void setIs_deleted(String is_deleted) {
//        this.is_deleted = is_deleted;
//    }

    public Integer getAlert_No() {
        return Alert_No;
    }

    public void setAlert_No(Integer alert_No) {
        Alert_No = alert_No;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getType_No() {
        return type_No;
    }

    public void setType_No(Integer type_No) {
        this.type_No = type_No;
    }
}
