package com.polytechnic.touristo_app.models;

import android.widget.ImageView;

public class home_touristPlaces_model {
    String name;
    int count;
    String image;

    public home_touristPlaces_model(String name, int count, String image) {
        this.name = name;
        this.count = count;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
