package com.project.Pojo;

import java.io.Serializable;


public class History implements Serializable {
    private Integer ID;
    private Integer history_No;
    private String phone_number;
    private String history_place;
    private String history_date;
    private String history_doctor;
    private String history_organ;
    private String symptom;
    private String conclusion;
    private String suggestion;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getHistory_No() {
        return history_No;
    }

    public void setHistory_No(Integer history_No) {
        this.history_No = history_No;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getHistory_place() {
        return history_place;
    }

    public void setHistory_place(String history_place) {
        this.history_place = history_place;
    }

    public String getHistory_date() {
        return history_date;
    }

    public void setHistory_date(String history_date) {
        this.history_date = history_date;
    }

    public String getHistory_doctor() {
        return history_doctor;
    }

    public void setHistory_doctor(String history_doctor) {
        this.history_doctor = history_doctor;
    }

    public String getHistory_organ() {
        return history_organ;
    }

    public void setHistory_organ(String history_organ) {
        this.history_organ = history_organ;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
