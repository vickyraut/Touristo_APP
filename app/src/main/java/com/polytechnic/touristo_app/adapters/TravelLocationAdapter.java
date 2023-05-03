package com.polytechnic.touristo_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.models.TravelLoacation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelLocationAdapter extends RecyclerView.Adapter<TravelLocationAdapter.TravelLocationViewHolder> {

    private List<TravelLoacation> travelLoacations;

    public TravelLocationAdapter(List<TravelLoacation> travelLoacations) {
        this.travelLoacations = travelLoacations;
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


        holder.setLocationData(travelLoacations.get(position));
    }

    @Override
    public int getItemCount() {

        return travelLoacations.size();
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
        void  setLocationData(TravelLoacation travelLoacation){
            Picasso.get().load(travelLoacation.getImageUrl()).into(kbvLocation);
            textTitle.setText(travelLoacation.getTitle());
            textLocation.setText(travelLoacation.getLocation());
            textStarRating.setText(String.valueOf((float) travelLoacation.getStarRating()));
        }
    }
}
