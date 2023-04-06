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
//        home_touristPlaces_model v = list.get(position);
        home_touristPlaces_model v =list.get(position);
        holder.t1.setText(v.getName());
        String count = String.valueOf(v.getCount());
        holder.t2.setText(count);

        Picasso.get()
                .load(Urls.TouristImageAddress + v.getImage())
                .error(R.drawable.reg_bg)
                .into(holder.i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        //calls freg
        ImageView i,i2;
        TextView t1,t2;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            //calls fragment
            i= itemView.findViewById(R.id.cv_hotels_img);
            t1=itemView.findViewById(R.id.tv_hotel_name);
            t2=itemView.findViewById(R.id.tv_likesCount);
        }


    }
}
