package com.polytechnic.touristo_app.models;

public class rec_for_you_model {
    int img_place,img_loc;
    String tv_location,tv_place_name;

    public rec_for_you_model(int img_place, int img_loc, String tv_location, String tv_place_name) {
        this.img_place = img_place;
        this.img_loc = img_loc;
        this.tv_location = tv_location;
        this.tv_place_name = tv_place_name;
    }

    public int getImg_place() {
        return img_place;
    }

    public void setImg_place(int img_place) {
        this.img_place = img_place;
    }

    public int getImg_loc() {
        return img_loc;
    }

    public void setImg_loc(int img_loc) {
        this.img_loc = img_loc;
    }

    public String getTv_location() {
        return tv_location;
    }

    public void setTv_location(String tv_location) {
        this.tv_location = tv_location;
    }

    public String getTv_place_name() {
        return tv_place_name;
    }

    public void setTv_place_name(String tv_place_name) {
        this.tv_place_name = tv_place_name;
    }
}
