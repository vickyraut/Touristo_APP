package com.polytechnic.touristo_app;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Home extends AppCompatActivity {

    boolean doubletap;
    private SmoothBottomBar smoothBottomBar;
    SearchFragment searchFragment;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        replace(new HomeFragment());

        smoothBottomBar = findViewById(R.id.bottomBar);

        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        replace(new HomeFragment());
                        break;
                    case 1:
                        replace(new ExploreFragment());
                        break;
                    case 2:
                        replace(new LikedFragment());
                        break;
                    case 3:
                        replace(new ProfileFragment());
                        break;
                }
                return false;
            }
        });

    }

    private void replace(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame, fragment);
        transaction.commit();
    }

    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragInstance = fm.findFragmentById(R.id.home_constraint);

        if (fragInstance instanceof SearchFragment){
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.home_frame, new HomeFragment());
            transaction.commit();
        }
        else if (doubletap) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press Again to Exit App", Toast.LENGTH_SHORT).show();
            doubletap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap = false;
                }
            }, 2000);
        }

    }

}