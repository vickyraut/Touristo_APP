package com.polytechnic.touristo_app.adapters;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.models.saved_rec_model;
import com.polytechnic.touristo_app.models.story_model;
import com.polytechnic.touristo_app.models.value_set_get;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class story_adapter extends RecyclerView.Adapter<story_adapter.viewholder>{
        ArrayList<story_model> list;
        Context con;


        public story_adapter(ArrayList<story_model> list, Context con) {
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
            story_model s = list.get(position);
            Picasso.get().load(s.getImage()).error(R.drawable.reg_bg).into(holder.i);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public class viewholder extends RecyclerView.ViewHolder {
            CircleImageView i;




            public viewholder(@NonNull View itemView) {
                super(itemView);

                i= itemView.findViewById(R.id.story_img);

            }


        }




    }



