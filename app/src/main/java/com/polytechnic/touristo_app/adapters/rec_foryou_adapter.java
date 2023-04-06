package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.models.rec_for_you_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class rec_foryou_adapter extends RecyclerView.Adapter<rec_foryou_adapter.viewholder> {

    ArrayList<rec_for_you_model> forYouModels;

    Context con;


    public rec_foryou_adapter(ArrayList<rec_for_you_model> list, Context con) {
        this.forYouModels = list;
        this.con = con;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(con).inflate(R.layout.home_sliding_cards, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        rec_for_you_model v = forYouModels.get(position);
        holder.loc_name.setText(v.getName());
        holder.loc_city.setText(v.getCity());

        Picasso.get()
                .load(Urls.TouristImageAddress + v.getImage())
                .error(R.drawable.reg_bg)
                .into(holder.loc_image);
    }

    @Override
    public int getItemCount() {
        return forYouModels.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        //calls freg
        ImageView loc_image;
        TextView loc_name, loc_city;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            //calls freg
            loc_image = itemView.findViewById(R.id.img_place);
            loc_name = itemView.findViewById(R.id.tv_place_name);
            loc_city = itemView.findViewById(R.id.tv_location);

        }


    }
}
