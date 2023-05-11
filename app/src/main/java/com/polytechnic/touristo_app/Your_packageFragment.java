package com.polytechnic.touristo_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.adapters.YourPackageAdapter;
import com.polytechnic.touristo_app.models.YourPackageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Your_packageFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<YourPackageModel> list = new ArrayList<>();
    YourPackageAdapter adapter;
    public Your_packageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_package, container, false);
        recyclerView = view.findViewById(R.id.see_all_recycler2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        showPackages();
        return view;
    }

    private void showPackages() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(Urls.urlShowTour, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getBuyedPackages");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        String time = jsonObject.getString("time");
                        String date = jsonObject.getString("date");
                        String mem = jsonObject.getString("members");
                        String trac = jsonObject.getString("transaction");
                        String price = jsonObject.getString("price");
                    list.add(new YourPackageModel(name, image, time, date, mem, trac, price));
                    }

                    adapter = new YourPackageAdapter(getContext(), list);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Toast.makeText(getContext(), "Server Error, Please try again later...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}