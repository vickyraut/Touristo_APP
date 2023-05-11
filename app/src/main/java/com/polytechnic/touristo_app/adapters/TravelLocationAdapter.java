package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.activity_hot;
import com.polytechnic.touristo_app.models.TravelLoacation;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

public class TravelLocationAdapter extends RecyclerView.Adapter<TravelLocationAdapter.TravelLocationViewHolder> {

    private List<TravelLoacation> list;
    Context con;

    public TravelLocationAdapter(List<TravelLoacation> travelLoacations, Context con) {
        this.list = travelLoacations;
        this.con = con;
    }

    @NonNull
    @Override
    public TravelLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TravelLocationViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.iitemcontainer,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TravelLocationViewHolder holder, int position) {
        TravelLoacation v = list.get(position);
        final TravelLoacation temp = list.get(position);

        Picasso.get().load(v.getImageUrl()).into(holder.kbvLocation);
        holder.textTitle.setText(v.getTitle());
        holder.textLocation.setText(v.getLocation());
        holder.textStarRating.setText(String.valueOf((float) v.getStarRating()));

        holder.kbvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, activity_hot.class);
                intent.putExtra("name", temp.getTitle());
                intent.putExtra("image", temp.getImageUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                con.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    static class  TravelLocationViewHolder extends RecyclerView.ViewHolder {



        private KenBurnsView kbvLocation;
        private TextView textTitle,textLocation,textStarRating;



        public TravelLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvLocation =itemView.findViewById(R.id.kbvlocation);
            textTitle =itemView.findViewById(R.id.textTitle);
            textLocation =itemView.findViewById(R.id.txt_location);
            textStarRating =itemView.findViewById(R.id.text_star_rating);

        }
    }
}
