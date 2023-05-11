package com.polytechnic.touristo_app;

import static android.widget.LinearLayout.HORIZONTAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.adapters.TravelLocationAdapter;
import com.polytechnic.touristo_app.adapters.saved_rec_adapter;
import com.polytechnic.touristo_app.adapters.story_adapter;
import com.polytechnic.touristo_app.models.TravelLoacation;
import com.polytechnic.touristo_app.models.saved_rec_model;
import com.polytechnic.touristo_app.models.story_model;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class activity_hot extends AppCompatActivity {
    RecyclerView recyclerV;
    ArrayList<story_model> story_models;
    story_adapter story_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_hot);
        getPlacesviaCountry();

        ImageView imageView = findViewById(R.id.activity_hot_images);
        TextView textView = findViewById(R.id.textView7);
        ImageButton imageButton = findViewById(R.id.btn_next);
        recyclerV= findViewById(R.id.recv_story);
        story_models = new ArrayList<>();

        textView.setText(getIntent().getStringExtra("name"));
        Picasso.get().load(getIntent().getStringExtra("image")).error(R.drawable.reg_bg).into(imageView);

        LinearLayoutManager manager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerV.setLayoutManager(manager);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InsideHotPlaces.class);
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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

                    story_adapter = new story_adapter(story_models, getApplicationContext());
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