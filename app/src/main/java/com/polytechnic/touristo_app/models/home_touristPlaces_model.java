package com.polytechnic.touristo_app.models;

public class home_touristPlaces_model {
    String name, city, country, image, description, days;
    int price, likes;
    double rating;
    double latitude, longitude;
    int liked_status;

    public home_touristPlaces_model(String name, String city, String country, String image, String description, String days, int price, int likes, double rating, double latitude, double longitude, int liked_status) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.image = image;
        this.description = description;
        this.days = days;
        this.price = price;
        this.likes = likes;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.liked_status = liked_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getLiked_status() {
        return liked_status;
    }

    public void setLiked_status(int liked_status) {
        this.liked_status = liked_status;
    }
}
