package com.enema.enemaapp.models;

public class MyBookingData {

    String booking_image, booking_course_name, booking_course_location, booking_rating, booking_rating_count, wallet_tnx_id, booking_session,
            booking_daydate,
            booking_time,
            booking_for,
            coupon_code,
            course_fee,
            course_provider_no,
            course_id;

    public MyBookingData(){ }


    public MyBookingData(String booking_image, String booking_course_name, String booking_course_location, String booking_rating, String booking_rating_count, String wallet_tnx_id, String booking_session, String booking_daydate, String booking_time, String booking_for, String coupon_code, String course_fee, String course_provider_no, String course_id) {
        this.booking_image = booking_image;
        this.booking_course_name = booking_course_name;
        this.booking_course_location = booking_course_location;
        this.booking_rating = booking_rating;
        this.booking_rating_count = booking_rating_count;
        this.wallet_tnx_id = wallet_tnx_id;
        this.booking_session = booking_session;
        this.booking_daydate = booking_daydate;
        this.booking_time = booking_time;
        this.booking_for = booking_for;
        this.coupon_code = coupon_code;
        this.course_fee = course_fee;
        this.course_provider_no = course_provider_no;
        this.course_id = course_id;
    }

    public String getBooking_image() {
        return booking_image;
    }

    public void setBooking_image(String booking_image) {
        this.booking_image = booking_image;
    }

    public String getBooking_course_name() {
        return booking_course_name;
    }

    public void setBooking_course_name(String booking_course_name) {
        this.booking_course_name = booking_course_name;
    }

    public String getBooking_course_location() {
        return booking_course_location;
    }

    public void setBooking_course_location(String booking_course_location) {
        this.booking_course_location = booking_course_location;
    }

    public String getBooking_rating() {
        return booking_rating;
    }

    public void setBooking_rating(String booking_rating) {
        this.booking_rating = booking_rating;
    }

    public String getBooking_rating_count() {
        return booking_rating_count;
    }

    public void setBooking_rating_count(String booking_rating_count) {
        this.booking_rating_count = booking_rating_count;
    }

    public String getWallet_tnx_id() {
        return wallet_tnx_id;
    }

    public void setWallet_tnx_id(String wallet_tnx_id) {
        this.wallet_tnx_id = wallet_tnx_id;
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

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public String getBooking_for() {
        return booking_for;
    }

    public void setBooking_for(String booking_for) {
        this.booking_for = booking_for;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCourse_fee() {
        return course_fee;
    }

    public void setCourse_fee(String course_fee) {
        this.course_fee = course_fee;
    }

    public String getCourse_provider_no() {
        return course_provider_no;
    }

    public void setCourse_provider_no(String course_provider_no) {
        this.course_provider_no = course_provider_no;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
