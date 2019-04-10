package com.enema.enemaapp.models;

public class AdSliderData {

    String ad_image, ad_punch_line, ad_day, ad_body, ad_cost, ad_click_data;

    public AdSliderData() {}

    public AdSliderData(String ad_image, String ad_punch_line, String ad_day, String ad_body, String ad_cost, String ad_click_data) {
        this.ad_image = ad_image;
        this.ad_punch_line = ad_punch_line;
        this.ad_day = ad_day;
        this.ad_body = ad_body;
        this.ad_cost = ad_cost;
        this.ad_click_data = ad_click_data;
    }

    public String getAd_image() {
        return ad_image;
    }

    public void setAd_image(String ad_image) {
        this.ad_image = ad_image;
    }

    public String getAd_punch_line() {
        return ad_punch_line;
    }

    public void setAd_punch_line(String ad_punch_line) {
        this.ad_punch_line = ad_punch_line;
    }

    public String getAd_day() {
        return ad_day;
    }

    public void setAd_day(String ad_day) {
        this.ad_day = ad_day;
    }

    public String getAd_body() {
        return ad_body;
    }

    public void setAd_body(String ad_body) {
        this.ad_body = ad_body;
    }

    public String getAd_cost() {
        return ad_cost;
    }

    public void setAd_cost(String ad_cost) {
        this.ad_cost = ad_cost;
    }

    public String getAd_click_data() {
        return ad_click_data;
    }

    public void setAd_click_data(String ad_click_data) {
        this.ad_click_data = ad_click_data;
    }
}
