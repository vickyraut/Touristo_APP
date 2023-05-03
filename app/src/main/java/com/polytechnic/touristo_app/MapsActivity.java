package com.polytechnic.touristo_app;

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.polytechnic.touristo_app.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final int REQUEST_LOCATION_PERMISSION = 1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    double latitude, longitude;
    String address;
    double user_latitude, user_longitude, temp_latitude, temp_longitude;
    String user_location, title;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        // getting user's location
        user_latitude = preferences.getFloat("latitude", 0);
        user_longitude = preferences.getFloat("longitude", 0);
        user_location = preferences.getString("city", "My Location");

        // getting location's coordinates
        temp_latitude = getIntent().getDoubleExtra("latitude", 0);
        temp_longitude = getIntent().getDoubleExtra("longitude", 0);
        Toast.makeText(this, String.valueOf(temp_latitude), Toast.LENGTH_LONG).show();
        Toast.makeText(this, String.valueOf(temp_longitude), Toast.LENGTH_LONG).show();
        title = getIntent().getStringExtra("title");

        binding.btnmyLocation.setOnClickListener(view -> getMyLocation());
        binding.btnMapType.setOnClickListener(view -> showDialog());
        binding.btnSelectedlocation.setOnClickListener(view -> setMarker(temp_latitude, temp_longitude, title));
        binding.btnPolyline.setOnClickListener(view -> setPolyline(user_latitude, user_longitude, temp_latitude, temp_longitude, title, user_location));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setPolyline(double user_latitude, double user_longitude, double temp_latitude, double temp_longitude, String title, String user_location) {
        // Add a marker in Destination and move the camera
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(temp_latitude, temp_longitude))
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(temp_latitude, temp_longitude), 5), 4000, null);

        // Add a marker in Current Location and move the camera
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(user_latitude, user_longitude))
                .title(user_location)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        // Add polyline between user's location and Destination
        mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(user_latitude, user_longitude), new LatLng(temp_latitude, temp_longitude))
                .width(5)
                .color(Color.BLUE)
                .geodesic(true));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void getMyLocation() {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();


                        Executor executor = Executors.newSingleThreadExecutor();

                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> addressList = null;

                        try {
                            addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                        address = addressList.get(0).getAddressLine(0) + " " + addressList.get(0).getLocality() + " " +
                                addressList.get(0).getCountryName();

                        String city = addressList.get(0).getLocality();

                        setMarker(latitude, longitude, address);

                        // save Important data to sharedpreference
                        editor.putFloat("latitude", (float) latitude).apply();
                        editor.putString("city", city).apply();
                        editor.putFloat("longitude", (float) longitude).apply();
                        editor.putString("address", address).apply();
                    }
                }
            });
        } else {
            askpermission();
        }
    }

    private void askpermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();
            } else {
                Toast.makeText(getApplicationContext(), "Please provide required permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showDialog() {

        final Dialog dialog = new Dialog(getApplicationContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.map_bottom_sheet);

        ImageButton normal = dialog.findViewById(R.id.idBtnNormalMap);
        ImageButton satellite = dialog.findViewById(R.id.idBtnSatelliteMap);
        ImageButton terain = dialog.findViewById(R.id.idBtnTerrainMap);
        ImageButton hybrid = dialog.findViewById(R.id.idBtnHybridMap);
        LinearLayout normalin = dialog.findViewById(R.id.idLinNormalMap);
        LinearLayout satellitein = dialog.findViewById(R.id.idLinSatelliteMap);
        LinearLayout terrainin = dialog.findViewById(R.id.idLinTerrainMap);
        LinearLayout hybridin = dialog.findViewById(R.id.idLinHybridMap);


        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(getApplicationContext(), "Default Map", Toast.LENGTH_SHORT).show();
            }
        });

        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Satellite Map", Toast.LENGTH_SHORT).show();
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        terain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                Toast.makeText(getApplicationContext(), "Terrain Map", Toast.LENGTH_SHORT).show();
            }
        });

        hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Toast.makeText(getApplicationContext(), "Hybrid Map", Toast.LENGTH_SHORT).show();
            }
        });

        normalin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(getApplicationContext(), "Default Map", Toast.LENGTH_SHORT).show();
            }
        });

        satellitein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Satellite Map", Toast.LENGTH_SHORT).show();
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        terrainin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                Toast.makeText(getApplicationContext(), "Terrain Map", Toast.LENGTH_SHORT).show();
            }
        });

        hybridin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Toast.makeText(getApplicationContext(), "Hybrid Map", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DailogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void setMarker(double latitude, double longitude, String name) {

        LatLng latLng = new LatLng(latitude, longitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(address);
        mMap.addMarker(markerOptions);

        //Use Animated Camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20), 4000, null);

        // Add circle to Location
        mMap.addCircle(new CircleOptions()
                .center(latLng)
                .fillColor(Color.parseColor("#FF03DAC5"))
                .strokeColor(Color.TRANSPARENT)
                .radius(8)
        );
    }
}