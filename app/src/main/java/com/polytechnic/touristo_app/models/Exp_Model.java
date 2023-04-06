package com.polytechnic.touristo_app.models;

public class Exp_Model {
    int iv_place;
    String name,price,days;

    public Exp_Model(int iv_place, String name, String price, String days) {
        this.iv_place = iv_place;
        this.name = name;
        this.price = price;
        this.days = days;
    }

    public int getIv_place() {
        return iv_place;
    }

    public void setIv_place(int iv_place) {
        this.iv_place = iv_place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
