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
import com.polytechnic.touristo_app.models.value_set_get;
import java.util.ArrayList;

public class recycleradapters extends RecyclerView.Adapter<recycleradapters.viewholder>{
    ArrayList<value_set_get> list;
    Context con;


    public recycleradapters(ArrayList<value_set_get> list, Context con) {
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


        value_set_get v = list.get(position);
        holder.i.setImageResource(v.getPic());
        holder.t1.setText(v.getTxt1());
        holder.t2.setText(v.getTxt2());





    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        ImageView i;


        TextView t1,t2;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            i= itemView.findViewById(R.id.img_place);
            t1=itemView.findViewById(R.id.tv_place_name);
            t2=itemView.findViewById(R.id.tv_location);


        }


    }


}
