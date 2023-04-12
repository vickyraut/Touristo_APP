package com.polytechnic.touristo_app;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Home extends AppCompatActivity {

    String ROOT_FRAGMENT_TAG;


    private SmoothBottomBar smoothBottomBar;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        replace(new HomeFragment());
//        setupNavigationComponent();


//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

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
        transaction.addToBackStack(null);
        transaction.commit();

    }

}