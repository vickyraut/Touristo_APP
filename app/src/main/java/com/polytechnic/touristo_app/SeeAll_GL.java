package com.polytechnic.touristo_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.adapters.rec_foryou_adapter;
import  com.polytechnic.touristo_app.adapters.seeAll_glAdapter;
import com.polytechnic.touristo_app.models.rec_for_you_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SeeAll_GL extends AppCompatActivity {

    ArrayList<rec_for_you_model> data;

    RecyclerView Rv_gl;

    seeAll_glAdapter seeAllAdapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String login_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeall_gl);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        login_email = preferences.getString("email", "");

       data = new ArrayList<>();

        Rv_gl= findViewById(R.id.see_all_recycler);

        LinearLayoutManager SeeAllManager = new LinearLayoutManager(SeeAll_GL.this,LinearLayoutManager.VERTICAL,false);
        Rv_gl.setLayoutManager(SeeAllManager);


        getForYouPlaces();

    }

    private void getForYouPlaces() {
        AsyncHttpClient client = new AsyncHttpClient();

        //Fetch data of 1 recyclerview
        RequestParams params = new RequestParams();
        //Fetch data of 1 recyclerview
        RequestParams params1 = new RequestParams();
        //Fetch User name
        RequestParams params2 = new RequestParams();

        params2.put("email", login_email);

        client.post(Urls.urlgetTouristPlaces, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("getTouristPlaces");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        String city = jsonObject.getString("city");
                        String country = jsonObject.getString("country");
                        String like_count = jsonObject.getString("likes");
                        int price = jsonObject.getInt("price");
                        data.add(new rec_for_you_model(id, name, image, city, country, like_count, price));
                    }

                    seeAllAdapter = new seeAll_glAdapter(data, SeeAll_GL.this);
                    Rv_gl.setAdapter(seeAllAdapter);

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