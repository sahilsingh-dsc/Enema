package com.enema.enemaapp.models;

public class CourseReqData {

    String req_image, req_name;

    public CourseReqData() {
    }

    public CourseReqData(String req_image, String req_name) {
        this.req_image = req_image;
        this.req_name = req_name;
    }

    public String getReq_image() {
        return req_image;
    }

    public String getReq_name() {
        return req_name;
    }
}
