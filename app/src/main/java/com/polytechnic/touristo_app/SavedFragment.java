package com.polytechnic.touristo_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.adapters.Explore_adapter;
import com.polytechnic.touristo_app.adapters.saved_rec_adapter;
import com.polytechnic.touristo_app.models.Exp_Model;
import com.polytechnic.touristo_app.models.saved_rec_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SavedFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<saved_rec_model> list;
    saved_rec_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView= view.findViewById(R.id.saved_place_recyclerview);

        GridLayoutManager manager= new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        getSavedPlaces();

        return view;
    }

    private void getSavedPlaces() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Urls.urlGetSavedPlaces, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getSavedPlaces");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String city  = jsonObject.getString("city");
                        String country  = jsonObject.getString("country");
                        String image  = jsonObject.getString("image");
                        String description  = jsonObject.getString("description");
                        String days  = jsonObject.getString("days");
                        int price  = jsonObject.getInt("price");
                        double rating  = jsonObject.getDouble("rating");
                        double latitude  = jsonObject.getDouble("latitude");
                        double longitude  = jsonObject.getDouble("longitude");
                        int liked_status = jsonObject.getInt("liked_status");
                        int id = jsonObject.getInt("id");

                        list.add(new saved_rec_model(name,city,country,image, description,days,price,id,rating,latitude,longitude,liked_status));
                    }

                    adapter = new saved_rec_adapter(list, getContext());
                    recyclerView.setAdapter(adapter);

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