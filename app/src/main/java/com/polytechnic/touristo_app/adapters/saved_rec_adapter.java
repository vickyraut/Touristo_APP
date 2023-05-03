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
import com.polytechnic.touristo_app.models.value_set_get;
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


            saved_rec_model s = list.get(position);
            holder.i.setImageResource(s.getPlace_img());
            holder.t1.setText(s.getPlace_name());





        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public class viewholder extends RecyclerView.ViewHolder {
            ImageView i;


            TextView t1;

            public viewholder(@NonNull View itemView) {
                super(itemView);

                i= itemView.findViewById(R.id.saved_place_img);
                t1=itemView.findViewById(R.id.saved_place_name);


            }


        }




}
