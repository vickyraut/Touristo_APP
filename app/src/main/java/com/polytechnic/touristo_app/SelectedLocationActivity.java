package com.polytechnic.touristo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polytechnic.touristo_app.Constants.Urls;
import com.squareup.picasso.Picasso;

public class SelectedLocationActivity extends AppCompatActivity {

    TextView tv_locationName, tv_locationPrice, tv_locationDays;
    ImageView img_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_location);

        tv_locationName = findViewById(R.id.textView8);
        tv_locationPrice = findViewById(R.id.tv_visit);
        tv_locationDays = findViewById(R.id.tv_open);
        img_location = findViewById(R.id.imageLoaction);

        String price = "$" + String.valueOf(getIntent().getIntExtra("price", 0));

        tv_locationName.setText(getIntent().getStringExtra("name"));
        tv_locationPrice.setText(price);
        tv_locationDays.setText(" /" + getIntent().getStringExtra("days") + " Days");

        Picasso.get()
                .load(Urls.TouristImageAddress + getIntent().getStringExtra("image"))
                .error(R.drawable.reg_bg)
                .into(img_location);
    }
}