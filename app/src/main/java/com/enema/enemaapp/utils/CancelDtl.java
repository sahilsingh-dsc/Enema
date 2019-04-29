package com.enema.enemaapp.utils;

public class CancelDtl {

    String cancel_date, cancel_reason, course_id, course_name, user_email, user_mobile, user_name;

    public CancelDtl(String cancel_date, String cancel_reason, String course_id, String course_name, String user_email, String user_mobile, String user_name) {
        this.cancel_date = cancel_date;
        this.cancel_reason = cancel_reason;
        this.course_id = course_id;
        this.course_name = course_name;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.user_name = user_name;
    }

    public String getCancel_date() {
        return cancel_date;
    }

    public void setCancel_date(String cancel_date) {
        this.cancel_date = cancel_date;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
