package com.polytechnic.touristo_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.squareup.picasso.Picasso;

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

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


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

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        login_email = preferences.getString("email", "");

        seeAllForyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SeeAll_GL.class);
                startActivity(i);
            }
        });

        card_searchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.super.onPause();
                card_searchview.setVisibility(View.INVISIBLE);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.home_constraint,new SearchFragment());
                transaction.commit();
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
        ViewPager2 locationsViewPager = view.findViewById(R.id.Vp_hotPlaces);

        List<TravelLoacation> travelLoacations = new ArrayList<>();

        TravelLoacation travelLocationiconEiffelTower = new TravelLoacation();
        travelLocationiconEiffelTower.imageUrl = "https://images.unsplash.com/photo-1543349689-9a4d426bee8e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1101&q=80";
        travelLocationiconEiffelTower.title = "France";
        travelLocationiconEiffelTower.location = "Eiffel tower";
        travelLocationiconEiffelTower.starRating = 4.8f;
        travelLoacations.add(travelLocationiconEiffelTower);


        TravelLoacation travelLocationiconTaj = new TravelLoacation();
        travelLocationiconTaj.imageUrl = "https://c4.wallpaperflare.com/wallpaper/419/140/867/closed-up-photo-of-pink-petaled-flower-wallpaper-preview.jpg";
        travelLocationiconTaj.title = "India";
        travelLocationiconTaj.location = "Taj Mahal";
        travelLocationiconTaj.starRating = 4.8f;
        travelLoacations.add(travelLocationiconTaj);

        TravelLoacation travelLocationiconMaldives = new TravelLoacation();
        travelLocationiconMaldives.imageUrl = "https://c4.wallpaperflare.com/wallpaper/773/987/659/maldives-dock-island-beach-wallpaper-preview.jpg";
        travelLocationiconMaldives.title = "India";
        travelLocationiconMaldives.location = "Maldives";
        travelLocationiconMaldives.starRating = 4.8f;
        travelLoacations.add(travelLocationiconMaldives);
        locationsViewPager.setAdapter(new TravelLocationAdapter(travelLoacations));

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

        getForYouPlaces();


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

                        for_you_models.add(new rec_for_you_model(id, name, image, city, country));
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
                        String image = jsonObject.getString("image");
                        int count = jsonObject.getInt("likes");

                        touristPlaces_models.add(new home_touristPlaces_model(name, count, image));
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
    }


}