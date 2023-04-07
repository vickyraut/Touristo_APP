package com.polytechnic.touristo_app.models;

public class Exp_Model {
    String Image;
    String name;
    int price,days;

    public Exp_Model(String image, String name, int price, int days) {
        Image = image;
        this.name = name;
        this.price = price;
        this.days = days;
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
