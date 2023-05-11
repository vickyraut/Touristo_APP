package com.polytechnic.touristo_app;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.adapters.Search_adapter;
import com.polytechnic.touristo_app.models.Exp_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class SearchFragment extends Fragment {

    SearchView searchView;
    RecyclerView search_recycler;
    Search_adapter search_adapter;
    List<Exp_Model> exp_models = new ArrayList<>();
    LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        layout = view.findViewById(R.id.search_linear);
        search_recycler = view.findViewById(R.id.search_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        search_recycler.setLayoutManager(layoutManager);

        searchView = view.findViewById(R.id.search_bar);
        searchView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom));
        searchView.setEnabled(true);

        getTop_TouristPlaces();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            searchView.setFocusedByDefault(true);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new HomeFragment());
                transaction.commit();
                Home.toolbar.setVisibility(View.VISIBLE);
            }
        });


        setupOnBackpressed();
        return view;
    }

    @SuppressLint("ResourceAsColor")
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
        search_adapter = new Search_adapter(templist, getContext());
        search_recycler.setAdapter(search_adapter);
    }


    private void setupOnBackpressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isEnabled()) {
                    assert getFragmentManager() != null;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, new HomeFragment());
                    transaction.commit();
                    setEnabled(false);
                }
            }
        });
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
                        int liked_status = jsonObject.getInt("liked_status");
                        int id = jsonObject.getInt("id");

                        exp_models.add(new Exp_Model(name,city,country,image, description,days,price,id,rating,latitude,longitude,liked_status));
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