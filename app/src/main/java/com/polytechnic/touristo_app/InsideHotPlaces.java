package com.polytechnic.touristo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.adapters.InsideHotPlacesAdapter;
import com.polytechnic.touristo_app.adapters.story_adapter;
import com.polytechnic.touristo_app.models.story_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class InsideHotPlaces extends AppCompatActivity {

    RecyclerView recyclerV;
    ArrayList<story_model> story_models;
    InsideHotPlacesAdapter story_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_hot_places);
        getPlacesviaCountry();

        recyclerV= findViewById(R.id.inside_hot_places_recycler);
        story_models = new ArrayList<>();

        LinearLayoutManager manager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerV.setLayoutManager(manager);
    }

    private void getPlacesviaCountry() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("country", getIntent().getStringExtra("name"));

        client.post(Urls.urlGeCountryViaPlaces, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
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

                        story_models.add(new story_model(name,city,country,image, description,days,price, likes, rating,latitude,longitude,liked_status));
                    }

                    story_adapter = new InsideHotPlacesAdapter(story_models, getApplicationContext());
                    recyclerV.setAdapter(story_adapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), "Server Error, Please try again later...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}