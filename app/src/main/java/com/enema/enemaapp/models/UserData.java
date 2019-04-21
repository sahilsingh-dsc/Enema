package com.enema.enemaapp.models;

public class UserData {

  private  String full_name, mobile_number, user_password;

    public UserData(){}

    public UserData(String full_name, String mobile_number, String user_password) {
        this.full_name = full_name;
        this.mobile_number = mobile_number;
        this.user_password = user_password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
