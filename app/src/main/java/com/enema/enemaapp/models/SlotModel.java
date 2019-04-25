package com.enema.enemaapp.models;

public class SlotModel  {

    String slot_date, slot_day, slot_month, slot_year, slot_id, time_from, time_to;

    public  SlotModel(){}

    public SlotModel(String slot_date, String slot_day, String slot_month, String slot_year, String slot_id, String time_from, String time_to) {
        this.slot_date = slot_date;
        this.slot_day = slot_day;
        this.slot_month = slot_month;
        this.slot_year = slot_year;
        this.slot_id = slot_id;
        this.time_from = time_from;
        this.time_to = time_to;
    }

    public String getSlot_date() {
        return slot_date;
    }

    public void setSlot_date(String slot_date) {
        this.slot_date = slot_date;
    }

    public String getSlot_day() {
        return slot_day;
    }

    public void setSlot_day(String slot_day) {
        this.slot_day = slot_day;
    }

    public String getSlot_month() {
        return slot_month;
    }

    public void setSlot_month(String slot_month) {
        this.slot_month = slot_month;
    }

    public String getSlot_year() {
        return slot_year;
    }

    public void setSlot_year(String slot_year) {
        this.slot_year = slot_year;
    }

    public String getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(String slot_id) {
        this.slot_id = slot_id;
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
