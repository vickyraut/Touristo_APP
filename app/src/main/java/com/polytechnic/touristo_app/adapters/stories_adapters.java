package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import  com.polytechnic.touristo_app.R;
import  com.polytechnic.touristo_app.models.stories_model;

import java.util.ArrayList;

public class stories_adapters extends RecyclerView.Adapter<stories_adapters.viewholder> {

    ArrayList<stories_model> list;

    Context con;

    public stories_adapters(ArrayList<stories_model> list, Context con) {
        this.list = list;
        this.con = con;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(con).inflate(R.layout.story_frag,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        stories_model v = list.get(position);
        holder.i.setImageResource(v.getCiv_stories());
    }

//    @Override
//    public void onBindViewHolder(@NonNull rec_foryou_adapter.viewholder holder, int position) {
//        stories_model v = list.get(position);
//        holder.i.setImageResource(v.getCiv_stories());
//
//    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        //calls freg
        ImageView i;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            //calls freg
            i= itemView.findViewById(R.id.story_img);

        }


    }
}
