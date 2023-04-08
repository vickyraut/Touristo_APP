package com.polytechnic.touristo_app.models;

public class Exp_Model {
    String Image;
    String name;

    int price;

    String days;

    public Exp_Model(String image, String name, int price, String days) {
        Image = image;
        this.name = name;
        this.days = days;
        this.price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
