package com.enema.enemaapp.models;

public class CouponData {
    String coupon_code, coupon_title, coupon_desc, coupon_value, coupon_type;

    public CouponData(){ }

    public CouponData(String coupon_code, String coupon_title, String coupon_desc, String coupon_value, String coupon_type) {
        this.coupon_code = coupon_code;
        this.coupon_title = coupon_title;
        this.coupon_desc = coupon_desc;
        this.coupon_value = coupon_value;
        this.coupon_type = coupon_type;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCoupon_title() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public String getCoupon_desc() {
        return coupon_desc;
    }

    public void setCoupon_desc(String coupon_desc) {
        this.coupon_desc = coupon_desc;
    }

    public String getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(String coupon_value) {
        this.coupon_value = coupon_value;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }
}
