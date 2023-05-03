package com.polytechnic.touristo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.SphericalUtil;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.models.Exp_Model;
import com.squareup.picasso.Picasso;

import java.util.prefs.Preferences;

public class SelectedLocationActivity extends AppCompatActivity {

    TextView tv_locationName, tv_country, tv_description, tv_rating, tv_locationPrice, tv_locationDays, tv_location_distance;
    ImageView img_location;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CardView cv_map;
    ExtendedFloatingActionButton fbtn_Book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_location);

        tv_locationName = findViewById(R.id.textView8);
        tv_country = findViewById(R.id.textView9);
        tv_locationPrice = findViewById(R.id.tv_visit);
        tv_locationDays = findViewById(R.id.tv_open);
        tv_description = findViewById(R.id.textView12);
        tv_location_distance = findViewById(R.id.textView10);
        tv_rating = findViewById(R.id.textview_rating);
        img_location = findViewById(R.id.imageLoaction);
        cv_map = findViewById(R.id.cardView100);
        fbtn_Book = findViewById(R.id.extendedFloatingActionButton);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

//         get  all data
        String name = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        String country = getIntent().getStringExtra("country");
        String imageUrl = getIntent().getStringExtra("image");
        int price = getIntent().getIntExtra("price", 0);
        String days = (getIntent().getStringExtra("days")+" Days");
        String description = getIntent().getStringExtra("description");
        float rating = getIntent().getFloatExtra("rating",0);


        // set all data
        tv_locationName.setText(name);
        tv_country.setText(city+", "+country);
        tv_locationPrice.setText("₹" + String.valueOf(price));
        tv_locationDays.setText(days);
        tv_rating.setText(String.valueOf(rating));
        tv_description.setText(description);

        if (getIntent().getStringExtra("image").isEmpty()){
            img_location.setImageResource(R.drawable.reg_bg);
        }else {
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.reg_bg)
                    .into(img_location);
        }


        // getting user's location
        double user_latitude = preferences.getFloat("latitude",0);
        double user_longitude = preferences.getFloat("longitude",0);

        // getting location's coordinates
        double temp_latitude= getIntent().getDoubleExtra("latitude",0);
        double temp_longitude=getIntent().getDoubleExtra("longitude",0);

        // Calculate distance
        double distance = calculatedistance(user_latitude,user_longitude,temp_latitude,temp_longitude);

        tv_location_distance.setText((String.format("%.2f",(distance/1000))+" Km"));

        cv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectedLocationActivity.this, MapsActivity.class);
                intent.putExtra("latitude",temp_longitude);
                intent.putExtra("longitude",temp_latitude);
                intent.putExtra("title",name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        fbtn_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedLocationActivity.this, payment_details_Activity.class);
                intent.putExtra("name",name);
                intent.putExtra("country",country);
                intent.putExtra("city",city);
                intent.putExtra("image",imageUrl);
                intent.putExtra("price", price);
                intent.putExtra("days",days);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private double calculatedistance(double user_latitude, double user_longitude, double temp_latitude, double temp_longitude) {

        LatLng user = new LatLng(user_latitude,user_longitude);
        LatLng location = new LatLng(temp_latitude,temp_longitude);

        return SphericalUtil.computeDistanceBetween(user,location);
    }

}