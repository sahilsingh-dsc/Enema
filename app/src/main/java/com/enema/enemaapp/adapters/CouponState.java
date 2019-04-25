package com.enema.enemaapp.adapters;

public class CouponState {

    String coupon_code, coupon_type, coupon_value;

    public CouponState(){}

    public CouponState(String coupon_code, String coupon_type, String coupon_value) {
        this.coupon_code = coupon_code;
        this.coupon_type = coupon_type;
        this.coupon_value = coupon_value;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(String coupon_value) {
        this.coupon_value = coupon_value;
    }
}
