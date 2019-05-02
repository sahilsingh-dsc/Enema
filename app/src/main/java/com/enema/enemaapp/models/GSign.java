package com.enema.enemaapp.models;

public class GSign {

    String user_mobile, full_name, user_email;

    public GSign(String user_mobile, String full_name, String user_email) {
        this.user_mobile = user_mobile;
        this.full_name = full_name;
        this.user_email = user_email;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
