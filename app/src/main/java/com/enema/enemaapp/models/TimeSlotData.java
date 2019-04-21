package com.enema.enemaapp.models;

public class TimeSlotData {

    String time_from, time_to;

    public TimeSlotData(String time_from, String time_to) {
        this.time_from = time_from;
        this.time_to = time_to;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }
}
