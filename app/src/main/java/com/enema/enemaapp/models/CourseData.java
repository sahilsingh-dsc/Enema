package com.enema.enemaapp.models;

public class CourseData {

    String course_name, course_image, course_rating, course_rating_count, course_discount_price, course_actual_price, course_best_seller_status;

    public CourseData() {}

    public CourseData(String course_name, String course_image, String course_rating, String course_rating_count, String course_discount_price, String course_actual_price, String course_best_seller_status) {
        this.course_name = course_name;
        this.course_image = course_image;
        this.course_rating = course_rating;
        this.course_rating_count = course_rating_count;
        this.course_discount_price = course_discount_price;
        this.course_actual_price = course_actual_price;
        this.course_best_seller_status = course_best_seller_status;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_image() {
        return course_image;
    }

    public void setCourse_image(String course_image) {
        this.course_image = course_image;
    }

    public String getCourse_rating() {
        return course_rating;
    }

    public void setCourse_rating(String course_rating) {
        this.course_rating = course_rating;
    }

    public String getCourse_rating_count() {
        return course_rating_count;
    }

    public void setCourse_rating_count(String course_rating_count) {
        this.course_rating_count = course_rating_count;
    }

    public String getCourse_discount_price() {
        return course_discount_price;
    }

    public void setCourse_discount_price(String course_discount_price) {
        this.course_discount_price = course_discount_price;
    }

    public String getCourse_actual_price() {
        return course_actual_price;
    }

    public void setCourse_actual_price(String course_actual_price) {
        this.course_actual_price = course_actual_price;
    }

    public String getCourse_best_seller_status() {
        return course_best_seller_status;
    }

    public void setCourse_best_seller_status(String course_best_seller_status) {
        this.course_best_seller_status = course_best_seller_status;
    }
}
