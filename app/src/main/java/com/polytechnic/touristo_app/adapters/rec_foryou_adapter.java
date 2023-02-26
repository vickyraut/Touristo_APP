package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import  com.polytechnic.touristo_app.R;
import  com.polytechnic.touristo_app.models.rec_for_you_model;

import java.util.ArrayList;

public class rec_foryou_adapter extends RecyclerView.Adapter<rec_foryou_adapter.viewholder> {

    ArrayList<rec_for_you_model> list;

    Context con;


    public rec_foryou_adapter(ArrayList<rec_for_you_model> list, Context con) {
        this.list = list;
        this.con = con;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(con).inflate(R.layout.home_sliding_cards,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        rec_for_you_model v = list.get(position);
        holder.i.setImageResource(v.getImg_place());
        holder.i2.setImageResource(v.getImg_loc());
        holder.t1.setText(v.getTv_location());
        holder.t2.setText(v.getTv_place_name());

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
            //calls freg
            i= itemView.findViewById(R.id.img_place);
            i2= itemView.findViewById(R.id.img_loc);

            t1=itemView.findViewById(R.id.tv_place_name);
            t2=itemView.findViewById(R.id.tv_location);



        }


    }
}
