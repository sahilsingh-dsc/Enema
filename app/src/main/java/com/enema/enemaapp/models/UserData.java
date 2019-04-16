package com.enema.enemaapp.models;

public class UserData {

  private  String full_name, mobile_number, user_password, user_gender, user_city_residence, user_city_on_id, user_dob, user_email;

    public UserData(){}

    public UserData(String full_name, String mobile_number, String user_password, String user_gender, String user_city_residence, String user_city_on_id, String user_dob, String user_email) {
        this.full_name = full_name;
        this.mobile_number = mobile_number;
        this.user_password = user_password;
        this.user_gender = user_gender;
        this.user_city_residence = user_city_residence;
        this.user_city_on_id = user_city_on_id;
        this.user_dob = user_dob;
        this.user_email = user_email;
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

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_city_residence() {
        return user_city_residence;
    }

    public void setUser_city_residence(String user_city_residence) {
        this.user_city_residence = user_city_residence;
    }

    public String getUser_city_on_id() {
        return user_city_on_id;
    }

    public void setUser_city_on_id(String user_city_on_id) {
        this.user_city_on_id = user_city_on_id;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
