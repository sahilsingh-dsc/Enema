package com.enema.enemaapp.models;

public class TimeSlotData {

    String time_from, time_to, time_slot_limit;

    public TimeSlotData(String time_from, String time_to, String time_slot_limit) {
        this.time_from = time_from;
        this.time_to = time_to;
        this.time_slot_limit = time_slot_limit;
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

    public String getTime_slot_limit() {
        return time_slot_limit;
    }

    public void setTime_slot_limit(String time_slot_limit) {
        this.time_slot_limit = time_slot_limit;
    }
}
