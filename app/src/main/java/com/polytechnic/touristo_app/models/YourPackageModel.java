package com.polytechnic.touristo_app.models;

public class YourPackageModel {
    String name, image, time, date, members, trac, price;

    public YourPackageModel(String name, String image, String time, String date, String members, String trac, String price) {
        this.name = name;
        this.image = image;
        this.time = time;
        this.date = date;
        this.members = members;
        this.trac = trac;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getTrac() {
        return trac;
    }

    public void setTrac(String trac) {
        this.trac = trac;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
