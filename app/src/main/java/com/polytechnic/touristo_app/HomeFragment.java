package com.polytechnic.touristo_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.adapters.TravelLocationAdapter;
import com.polytechnic.touristo_app.adapters.home_tourist_adapter;
import com.polytechnic.touristo_app.adapters.rec_foryou_adapter;
import com.polytechnic.touristo_app.models.TravelLoacation;
import com.polytechnic.touristo_app.models.home_touristPlaces_model;
import com.polytechnic.touristo_app.models.rec_for_you_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    RecyclerView forYou_recyclerView, tourist_places_recyclerView;
    ArrayList<rec_for_you_model> for_you_models;
    ArrayList<home_touristPlaces_model> touristPlaces_models;
    rec_foryou_adapter rec_foryou_adapter;
    home_tourist_adapter home_tourist_adapter;
    TextView searchview_text;
    CardView card_searchview;
    ImageView img_myLocation;
    TextView tv_mylocation;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<TravelLoacation> travelLoacations = new ArrayList<>();
    TravelLocationAdapter travelLocationAdapter;
    ViewPager2 locationsViewPager;


    String login_email, Name;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView seeAllForyou = view.findViewById(R.id.Tv_seeAll);
        @SuppressLint("CutPasteId") TextView seeAllStories = view.findViewById(R.id.Tv_seeAll);

        searchview_text = view.findViewById(R.id.textView3);
        card_searchview = view.findViewById(R.id.cardView2);
        img_myLocation = view.findViewById(R.id.imageView7);
        tv_mylocation = view.findViewById(R.id.textView);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        Home home = new Home();
        tv_mylocation.setText(preferences.getString("city", "My Location"));

        login_email = preferences.getString("email", "");

        Home.toolbar.setVisibility(View.VISIBLE);

        getForYouPlaces();

        seeAllForyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SeeAll_GL.class);
                startActivity(i);
            }
        });

        img_myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_mylocation.setText(preferences.getString("city", "My Location"));
            }
        });

        card_searchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.super.onPause();
                card_searchview.setVisibility(View.INVISIBLE);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.home_constraint, new SearchFragment());
                transaction.commit();
                Home.toolbar.setVisibility(View.INVISIBLE);
            }
        });

        forYou_recyclerView = view.findViewById(R.id.rec_foryou);
        tourist_places_recyclerView = view.findViewById(R.id.home_rec_tourist_places);

        for_you_models = new ArrayList<>();
        touristPlaces_models = new ArrayList<>();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        forYou_recyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        tourist_places_recyclerView.setLayoutManager(layoutManager1);

        //kinberns view .........
        locationsViewPager = view.findViewById(R.id.Vp_hotPlaces);

        locationsViewPager.setClipToPadding(false);
        locationsViewPager.setClipChildren(false);
        locationsViewPager.setOffscreenPageLimit(3);
        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {

            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);

            }
        });
        locationsViewPager.setPageTransformer(compositePageTransformer);


        return view;
    }

    private void getForYouPlaces() {
        AsyncHttpClient client = new AsyncHttpClient();

        //Fetch data of 1 recyclerview
        RequestParams params = new RequestParams();
        //Fetch data of 1 recyclerview
        RequestParams params1 = new RequestParams();
        //Fetch User name
        RequestParams params2 = new RequestParams();
        //Fetch Hot Places
        RequestParams params3 = new RequestParams();

        params2.put("email", login_email);

        client.post(Urls.urlgetTouristPlaces, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("getTouristPlaces");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String city = jsonObject.getString("city");
                        String country = jsonObject.getString("country");
                        String image = jsonObject.getString("image");
                        String description = jsonObject.getString("description");
                        String days = jsonObject.getString("days");
                        int price = jsonObject.getInt("price");
                        int likes = jsonObject.getInt("likes");
                        double rating = jsonObject.getDouble("rating");
                        double latitude = jsonObject.getDouble("latitude");
                        double longitude = jsonObject.getDouble("longitude");

                        for_you_models.add(new rec_for_you_model(name, city, country, image, description, days, price, likes, rating, latitude, longitude));
                    }

                    rec_foryou_adapter = new rec_foryou_adapter(for_you_models, getContext());
                    forYou_recyclerView.setAdapter(rec_foryou_adapter);

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

        client.post(Urls.urlgetRandom_TouristPlaces, params1, new JsonHttpResponseHandler() {
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
                        int likes = jsonObject.getInt("likes");
                        double rating  = jsonObject.getDouble("rating");
                        double latitude  = jsonObject.getDouble("latitude");
                        double longitude  = jsonObject.getDouble("longitude");
                        int liked_status = jsonObject.getInt("liked_status");

                        touristPlaces_models.add(new home_touristPlaces_model(name,city,country,image, description,days,price, likes, rating,latitude,longitude,liked_status));
                    }


                    home_tourist_adapter = new home_tourist_adapter(touristPlaces_models, getContext());
                    tourist_places_recyclerView.setAdapter(home_tourist_adapter);

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

        client.post(Urls.urlgetMyDetails, params2, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getMyDetails");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String firstname = jsonObject.getString("firstname");

                        searchview_text.setText(String.format("Hi,%s ,where do you want to go?", firstname));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        client.post(Urls.urlGetHotPlaces, params3, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("getHotPlaces");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String location = jsonObject.getString("location");
                        String image = jsonObject.getString("image");
                        double star_rating = jsonObject.getDouble("star_rating");

                        travelLoacations.add(new TravelLoacation(title, location, image, star_rating));
                    }

                    travelLocationAdapter = new TravelLocationAdapter(travelLoacations);
                    locationsViewPager.setAdapter(travelLocationAdapter);

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