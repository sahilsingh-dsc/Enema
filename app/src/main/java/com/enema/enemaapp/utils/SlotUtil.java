package com.enema.enemaapp.utils;

public class SlotUtil {

    String booking_session, booking_daydate;

    public SlotUtil(String booking_session, String booking_daydate) {
        this.booking_session = booking_session;
        this.booking_daydate = booking_daydate;
    }

    public String getBooking_session() {
        return booking_session;
    }

    public void setBooking_session(String booking_session) {
        this.booking_session = booking_session;
    }

    public String getBooking_daydate() {
        return booking_daydate;
    }

    public void setBooking_daydate(String booking_daydate) {
        this.booking_daydate = booking_daydate;
    }
}
