package com.polytechnic.touristo_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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

    RecyclerView forYou_recyclerView,tourist_places_recyclerView;
    ArrayList<rec_for_you_model> for_you_models;
    ArrayList<home_touristPlaces_model> touristPlaces_models;
    rec_foryou_adapter rec_foryou_adapter;
    home_tourist_adapter home_tourist_adapter;
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

        seeAllForyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SeeAll_GL.class);
                startActivity(i);
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

        getForYouPlaces();


        return view;
    }

    private void getForYouPlaces() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestParams params1 = new RequestParams();

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

                        for_you_models.add(new rec_for_you_model(id,name,image,city,country));
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

        client.post(Urls.urlgetRandom_TouristPlaces,params1,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getTouristPlaces");

                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        int count = jsonObject.getInt("likes");

                        touristPlaces_models.add(new home_touristPlaces_model(name,count,image));
                    }

                    home_tourist_adapter = new home_tourist_adapter(touristPlaces_models,getContext());
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
    }
}