package com.enema.enemaapp.models;

public class MyBookingData {

    String booking_image, booking_course_name, booking_course_location, booking_rating, booking_rating_count, wallet_tnx_id;

    public MyBookingData(){ }

    public MyBookingData(String booking_image, String booking_course_name, String booking_course_location, String booking_rating, String booking_rating_count, String wallet_tnx_id) {
        this.booking_image = booking_image;
        this.booking_course_name = booking_course_name;
        this.booking_course_location = booking_course_location;
        this.booking_rating = booking_rating;
        this.booking_rating_count = booking_rating_count;
        this.wallet_tnx_id = wallet_tnx_id;
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
}
