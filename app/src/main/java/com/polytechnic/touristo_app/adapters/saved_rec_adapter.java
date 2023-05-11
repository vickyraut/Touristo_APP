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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.SelectedLocationActivity;
import com.polytechnic.touristo_app.models.Exp_Model;
import com.polytechnic.touristo_app.models.saved_rec_model;
import com.polytechnic.touristo_app.models.value_set_get;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class saved_rec_adapter extends RecyclerView.Adapter<saved_rec_adapter.viewholder>{
        ArrayList<saved_rec_model> list;
        Context con;


        public saved_rec_adapter(ArrayList<saved_rec_model> list, Context con) {
            this.list = list;
            this.con = con;
        }



        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(con).inflate(R.layout.saved_rec_design,parent,false);

            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            final saved_rec_model temp = list.get(position);

            saved_rec_model s = list.get(position);
            if (s.getImage().isEmpty()){
                holder.i.setImageResource(R.drawable.reg_bg);
            }else {
                Picasso.get()
                        .load(s.getImage())
                        .error(R.drawable.reg_bg)
                        .into(holder.i);
            }
            holder.t1.setText(s.getName());


            holder.c.setOnClickListener(new View.OnClickListener() {
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


        public class viewholder extends RecyclerView.ViewHolder {
            ImageView i;

            CardView c;

            TextView t1;

            public viewholder(@NonNull View itemView) {
                super(itemView);

                i= itemView.findViewById(R.id.saved_place_img);
                t1=itemView.findViewById(R.id.saved_place_name);
                c=itemView.findViewById(R.id.saved_cardView);


            }


        }




}
