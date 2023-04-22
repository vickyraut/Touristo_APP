package com.polytechnic.touristo_app;

import static android.content.Context.LOCATION_SERVICE;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.adapters.Map_Searchbar_Adapter;
import com.polytechnic.touristo_app.databinding.FragmentMapBinding;
import com.polytechnic.touristo_app.models.Exp_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;

public class MapFragment extends Fragment {


    FragmentMapBinding binding;
    public static final int REQUEST_LOCATION_PERMISSION = 1;
    double latitude, longitude;
    String address;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public SearchView searchView;
    RecyclerView search_recycler;
    Map_Searchbar_Adapter map_searchbar_adapter;
    List<Exp_Model> exp_models = new ArrayList<>();


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false);

        getTop_TouristPlaces();
        
        binding.btnmyLocation.setOnClickListener(view -> getMyLocation());
        binding.btnMapType.setOnClickListener(view -> showDialog());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.mapSearchRecyclerview.setLayoutManager(layoutManager);
        
        // Searchbar query change code
        binding.mapSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchByName(newText);
                return false;
            }
        });
        
        return binding.getRoot();
    }

    private void searchByName(String query) {
        List<Exp_Model> templist = new ArrayList<>();
        templist.clear();

        if (!query.isEmpty()) {
            for (Exp_Model data : exp_models) {
                if (data.getName().toUpperCase().contains(query.toUpperCase())) {
                    templist.add(data);
                }
            }
        } else {
            templist.clear();
        }
        map_searchbar_adapter = new Map_Searchbar_Adapter(templist, getContext());
        binding.mapSearchRecyclerview.setAdapter(map_searchbar_adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.Map_mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap = googleMap;


        }
    };



    private void getMyLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();


                        Executor executor = Executors.newSingleThreadExecutor();

                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addressList = null;

                        try {
                            addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                        address = addressList.get(0).getAddressLine(0) + " " + addressList.get(0).getLocality() + " " +
                                addressList.get(0).getCountryName();

                        String city = addressList.get(0).getLocality();

                        setMarker(latitude,longitude,address);

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
        requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();
            } else {
                Toast.makeText(getContext(), "Please provide required permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void setMarker(double latitude, double longitude, String name) {

        LatLng latLng = new LatLng(latitude,longitude);

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

    private void showDialog() {

        final Dialog dialog = new Dialog(getContext());
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
                Toast.makeText(getContext(), "Default Map", Toast.LENGTH_SHORT).show();
            }
        });

        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Satellite Map", Toast.LENGTH_SHORT).show();
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        terain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                Toast.makeText(getContext(), "Terrain Map", Toast.LENGTH_SHORT).show();
            }
        });

        hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Toast.makeText(getContext(), "Hybrid Map", Toast.LENGTH_SHORT).show();
            }
        });

        normalin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(getContext(), "Default Map", Toast.LENGTH_SHORT).show();
            }
        });

        satellitein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Satellite Map", Toast.LENGTH_SHORT).show();
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        terrainin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                Toast.makeText(getContext(), "Terrain Map", Toast.LENGTH_SHORT).show();
            }
        });

        hybridin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Toast.makeText(getContext(), "Hybrid Map", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DailogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void getTop_TouristPlaces() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Urls.urlgetTop_TouristPlaces, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getTouristPlaces");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String city  = jsonObject.getString("city");
                        String country  = jsonObject.getString("country");
                        String image  = jsonObject.getString("image");
                        String description  = jsonObject.getString("description");
                        String days  = jsonObject.getString("days");
                        int price  = jsonObject.getInt("price");
                        int likes  = jsonObject.getInt("likes");
                        int rating  = jsonObject.getInt("rating");
                        double latitude  = jsonObject.getDouble("latitude");
                        double longitude  = jsonObject.getDouble("longitude");

                        exp_models.add(new Exp_Model(name,city,country,image, description,days,price,likes,rating,latitude,longitude));
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Toast.makeText(getContext(), "Server Error, Please try again later...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}