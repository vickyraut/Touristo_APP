package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.SelectedLocationActivity;
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
        final rec_for_you_model temp = forYouModels.get(position);

        rec_for_you_model v = forYouModels.get(position);

        holder.loc_name.setText(v.getName());
        holder.loc_city.setText(v.getCity()+", "+v.getCountry());

        if (v.getImage().isEmpty()){
            holder.loc_image.setImageResource(R.drawable.reg_bg);
        }else {
            Picasso.get()
                    .load(v.getImage())
                    .error(R.drawable.reg_bg)
                    .into(holder.loc_image);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, SelectedLocationActivity.class);
                intent.putExtra("name",temp.getName());
                intent.putExtra("country",temp.getCountry());
                intent.putExtra("city",temp.getCity());
                intent.putExtra("image",temp.getImage());
                intent.putExtra("price", temp.getPrice());
                intent.putExtra("days",temp.getDays());
                intent.putExtra("rating",(float)temp.getRating());
                intent.putExtra("description",temp.getDescription());
                intent.putExtra("latitude",temp.getLatitude());
                intent.putExtra("longitude",temp.getLongitude());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                con.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return forYouModels.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        //calls freg
        ImageView loc_image;
        TextView loc_name, loc_city;
        CardView cardView;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            //calls freg
            loc_image = itemView.findViewById(R.id.img_place);
            loc_name = itemView.findViewById(R.id.tv_place_name);
            loc_city = itemView.findViewById(R.id.tv_location);
            cardView = itemView.findViewById(R.id.rec_recycler_cardview);

        }


    }
}
