package com.enema.enemaapp.models;

public class NotificationData {

    String noti_message;

    NotificationData(){}

    public NotificationData(String noti_message) {
        this.noti_message = noti_message;
    }

    public String getNoti_message() {
        return noti_message;
    }
}
