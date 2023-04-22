package com.polytechnic.touristo_app.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.MapFragment;
import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.SelectedLocationActivity;
import com.polytechnic.touristo_app.databinding.FragmentMapBinding;
import com.polytechnic.touristo_app.models.Exp_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Map_Searchbar_Adapter extends RecyclerView.Adapter<Map_Searchbar_Adapter.ExpViewHolder> {

    Context con;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private List<Exp_Model> mList = new ArrayList<>();
    MapFragment mapFragment = new MapFragment();

    public Map_Searchbar_Adapter(List<Exp_Model> mList, Context con) {
        this.mList = mList;
        this.con = con;
    }

    @NonNull
    @Override
    public Map_Searchbar_Adapter.ExpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_search_recycler, parent, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        editor = preferences.edit();

        return new Map_Searchbar_Adapter.ExpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Map_Searchbar_Adapter.ExpViewHolder holder, int position) {

        final Exp_Model temp = mList.get(position);

        Exp_Model v = mList.get(position);
        holder.location_name.setText(v.getName());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.slide_out_bottom));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ExpViewHolder extends RecyclerView.ViewHolder {
        TextView location_name;
        CardView cardView;

        public ExpViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.search_cardView1);
            location_name = itemView.findViewById(R.id.search_locationName1);
        }
    }
}
