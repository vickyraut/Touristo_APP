package com.polytechnic.touristo_app.models;

public class saved_rec_model {
    int place_img;
    String place_name;

    public saved_rec_model(int place_img, String place_name) {
        this.place_img = place_img;
        this.place_name = place_name;
    }

    public int getPlace_img() {
        return place_img;
    }

    public void setPlace_img(int place_img) {
        this.place_img = place_img;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }
}
