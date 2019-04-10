package com.enema.enemaapp.models;

public class LocationData {

    String loaction_name, location_image;

    public LocationData(){}

    public LocationData(String loaction_name, String location_image) {
        this.loaction_name = loaction_name;
        this.location_image = location_image;
    }

    public String getLoaction_name() {
        return loaction_name;
    }

    public void setLoaction_name(String loaction_name) {
        this.loaction_name = loaction_name;
    }

    public String getLocation_image() {
        return location_image;
    }

    public void setLocation_image(String location_image) {
        this.location_image = location_image;
    }
}
