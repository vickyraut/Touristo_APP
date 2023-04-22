package com.polytechnic.touristo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.SphericalUtil;
import com.polytechnic.touristo_app.Constants.Urls;
import com.squareup.picasso.Picasso;

import java.util.prefs.Preferences;

public class SelectedLocationActivity extends AppCompatActivity {

    TextView tv_locationName, tv_locationPrice, tv_locationDays, tv_location_distance;
    ImageView img_location;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_location);

        tv_locationName = findViewById(R.id.textView8);
        tv_locationPrice = findViewById(R.id.tv_visit);
        tv_locationDays = findViewById(R.id.tv_open);
        tv_location_distance = findViewById(R.id.textView10);
        img_location = findViewById(R.id.imageLoaction);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        String price = "$" + String.valueOf(getIntent().getIntExtra("price", 0));

        tv_locationName.setText(getIntent().getStringExtra("name"));
        tv_locationPrice.setText(price);
        tv_locationDays.setText(" /" + getIntent().getStringExtra("days") + " Days");

        // getting user's location
        double user_latitude = preferences.getFloat("latitude",0);
        double user_longitude = preferences.getFloat("longitude",0);

        // getting location's coordinates
        double temp_latitude= getIntent().getDoubleExtra("latitude",0);
        double temp_longitude=getIntent().getDoubleExtra("longitude",0);

        // Calculate distance
        double distance = calculatedistance(user_latitude,user_longitude,temp_latitude,temp_longitude);

        tv_location_distance.setText((String.format("%.2f",(distance/1000))+" Km"));

        if (getIntent().getStringExtra("image").isEmpty()){
            img_location.setImageResource(R.drawable.reg_bg);
        }else {
            Picasso.get()
                    .load(getIntent().getStringExtra("image"))
                    .error(R.drawable.reg_bg)
                    .into(img_location);
        }

    }

    private double calculatedistance(double user_latitude, double user_longitude, double temp_latitude, double temp_longitude) {

        LatLng user = new LatLng(user_latitude,user_longitude);
        LatLng location = new LatLng(temp_latitude,temp_longitude);

        return SphericalUtil.computeDistanceBetween(user,location);
    }
}