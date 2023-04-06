package com.polytechnic.touristo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SelectedLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_location);
        getSupportActionBar().hide();
    }
}