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
import  com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.models.rec_for_you_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class seeAll_glAdapter extends RecyclerView.Adapter<seeAll_glAdapter.viewholder> {

        ArrayList<rec_for_you_model> list;

        Context con;


        public seeAll_glAdapter(ArrayList<rec_for_you_model> list, Context con) {
            this.list = list;
            this.con = con;
        }


        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(con).inflate(R.layout.see_all_recycler,parent,false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            rec_for_you_model v = list.get(position);

            holder.t1.setText(v.getName());
            String price = "$" + String.valueOf(v.getPrice());
            holder.t2.setText(price);
            holder.t3.setText(v.getLike_count());

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
            ImageView i;
            TextView t1,t2,t3;


            public viewholder(@NonNull View itemView) {
                super(itemView);
                //calls freg
                i= itemView.findViewById(R.id.cv_hotels_img);
                t1= itemView.findViewById(R.id.location_name);
                t2= itemView.findViewById(R.id.tv_price);
                t3= itemView.findViewById(R.id.tv_likesCount);
            }

        }

    }



