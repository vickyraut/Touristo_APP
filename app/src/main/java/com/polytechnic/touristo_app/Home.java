package com.polytechnic.touristo_app;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Home extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_CLOSE = 0;
    private static final int POS_Home = 1;
    private static final int POS_TRENDING = 2;
    private static final int POS_EXPLORE = 3;
    private static final int POS_MY_PROFILE = 4;
    private static final int POS_SETTINGS = 5;
    private static final int POS_ABOUT_US = 6;
    private static final int POS_LOGOUT = 8;
    boolean doubletap;
    SearchFragment searchFragment;
    SlidingRootNav slidingRootNav;
    private SmoothBottomBar smoothBottomBar;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    FusedLocationProviderClient fusedLocationProviderClient;
    double latitude, longitude;
    String address;
    String city;
    public final static int REQUEST_CODE = 100;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static Toolbar toolbar;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        replace(new HomeFragment());
        getLastLocation();

        smoothBottomBar = findViewById(R.id.bottomBar);

        preferences = PreferenceManager.getDefaultSharedPreferences(Home.this);
        editor = preferences.edit();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcon();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_Home),
                createItemFor(POS_TRENDING),
                createItemFor(POS_EXPLORE),
                createItemFor(POS_MY_PROFILE),
                createItemFor(POS_SETTINGS),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)
        ));

        adapter.setListener(this);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_Home);

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
                        replace(new SavedFragment());
                        break;
                    case 3:
                        replace(new ProfileFragment());
                        break;
                }
                return false;
            }
        });

    }

    public DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(R.color.teal_200)
                .withTextTint(R.color.black)
                .withSelectedIconTint(R.color.teal_200)
                .withSelectedTextTint(R.color.teal_200);
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);

    }

    private Drawable[] loadScreenIcon() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void OnItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_Home) {
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.container, homeFragment);
        } else if (position == POS_TRENDING) {
            ExploreFragment exploreFragment = new ExploreFragment();
            transaction.replace(R.id.container, exploreFragment);
        } else if (position == POS_EXPLORE) {
            SavedFragment savedFragment = new SavedFragment();
            transaction.replace(R.id.container, savedFragment);
        } else if (position == POS_MY_PROFILE) {
            ProfileFragment profileFragment = new ProfileFragment();
            transaction.replace(R.id.container, profileFragment);
        } else if (position == POS_SETTINGS) {
            SettingsFragment settingsFragment = new SettingsFragment();
            transaction.replace(R.id.container, settingsFragment);
        } else if (position == POS_ABOUT_US) {
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            transaction.replace(R.id.container, aboutUsFragment);
        } else if (position == POS_LOGOUT) {
            finish();
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replace(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragInstance = fm.findFragmentById(R.id.home_constraint);

        if (slidingRootNav.isMenuOpened()) {
            slidingRootNav.closeMenu();
        } else if (fragInstance instanceof SearchFragment) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.container, new HomeFragment());
            transaction.commit();
        } else if (doubletap) {
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

    private void getLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();


                                Executor executor = Executors.newSingleThreadExecutor();

                                        Geocoder geocoder = new Geocoder(Home.this, Locale.getDefault());
                                        List<Address> addressList = null;

                                try {
                                    addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }


                                address = addressList.get(0).getAddressLine(0) + " " + addressList.get(0).getLocality() + " " +
                                                addressList.get(0).getCountryName();

                                        city = addressList.get(0).getLocality();



                                // save Important data to sharedpreference
                                editor.putFloat("latitude", (float) latitude).apply();
                                editor.putString("city", city).apply();
                                editor.putFloat("longitude", (float) longitude).apply();
                                editor.putString("address",address).apply();
                            }
                        }
                    });
        } else {
            askpermission();
        }
    }

    private void askpermission() {
        ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Please provide required permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}