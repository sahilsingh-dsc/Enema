package com.enema.enemaapp.models;

public class WishListData {

    String course_name, course_image, course_rating, course_area, course_city, course_rating_count, course_id;

    public WishListData(String course_name, String course_image, String course_rating, String course_area, String course_city, String course_rating_count, String course_id) {
        this.course_name = course_name;
        this.course_image = course_image;
        this.course_rating = course_rating;
        this.course_area = course_area;
        this.course_city = course_city;
        this.course_rating_count = course_rating_count;
        this.course_id = course_id;
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

    public String getCourse_area() {
        return course_area;
    }

    public void setCourse_area(String course_area) {
        this.course_area = course_area;
    }

    public String getCourse_city() {
        return course_city;
    }

    public void setCourse_city(String course_city) {
        this.course_city = course_city;
    }

    public String getCourse_rating_count() {
        return course_rating_count;
    }

    public void setCourse_rating_count(String course_rating_count) {
        this.course_rating_count = course_rating_count;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
