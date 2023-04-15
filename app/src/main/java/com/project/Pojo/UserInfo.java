package com.project.Pojo;

public class UserInfo {
    String phone_number;
    String username;
    String email;
    String birthday;
    String sex;

    public UserInfo() {
    }

    public UserInfo(String phone_number, String username, String email, String birthday, String sex) {
        this.phone_number = phone_number;
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
