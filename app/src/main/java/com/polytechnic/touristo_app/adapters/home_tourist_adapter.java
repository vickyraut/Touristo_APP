package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.content.Intent;
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

import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.SelectedLocationActivity;
import com.polytechnic.touristo_app.models.home_touristPlaces_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class home_tourist_adapter extends RecyclerView.Adapter<home_tourist_adapter.viewholder> {

    ArrayList<home_touristPlaces_model> list;

    Context con;


    public home_tourist_adapter(ArrayList<home_touristPlaces_model> list, Context con) {
        this.list = list;
        this.con = con;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(con).inflate(R.layout.home_tourist_recv,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final home_touristPlaces_model temp = list.get(position);
        home_touristPlaces_model v =list.get(position);

        holder.t1.setText(v.getName());
        holder.t2.setText(String.valueOf(v.getLikes()));

        if (v.getImage().isEmpty()){
            holder.i.setImageResource(R.drawable.reg_bg);
        }else {
            Picasso.get()
                    .load(v.getImage())
                    .error(R.drawable.reg_bg)
                    .into(holder.i);
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
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        //calls freg
        ImageView i;
        TextView t1,t2;
        CardView cardView;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            //calls fragment
            i= itemView.findViewById(R.id.cv_hotels_img);
            t1=itemView.findViewById(R.id.tv_hotel_name);
            t2=itemView.findViewById(R.id.tv_likesCount);
            cardView = itemView.findViewById(R.id.home_touristo_cardView);

        }

    }
}
