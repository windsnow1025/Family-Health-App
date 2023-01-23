package com.project.Pojo;

public class UserInfo {
    private String username;
    private String email;
    private String sex;
    private String birthday;
    public UserInfo(){}

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }

    public String getUsername() {
        return username;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
