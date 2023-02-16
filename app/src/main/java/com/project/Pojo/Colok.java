package com.project.Pojo;

public class Colok {
    private Integer ID;
    private String phone_number;
    private Integer history_No;
    private String date;
    private String tilte;
    private String cycle;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHistory_No(Integer history_No) {
        this.history_No = history_No;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Integer getID() {
        return ID;
    }

    public String getCycle() {
        return cycle;
    }

    public String getDate() {
        return date;
    }

    public Integer getHistory_No() {
        return history_No;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getTilte() {
        return tilte;
    }
}
