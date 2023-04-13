package com.polytechnic.touristo_app.models;

public class rec_for_you_model {
    String id, name, image,city, country, like_count;
    int price;

    public rec_for_you_model(String id, String name, String image, String city, String country, String like_count, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.city = city;
        this.country = country;
        this.like_count = like_count;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
