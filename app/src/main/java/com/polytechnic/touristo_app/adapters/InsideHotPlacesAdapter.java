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
import com.polytechnic.touristo_app.models.rec_for_you_model;
import com.polytechnic.touristo_app.models.saved_rec_model;
import com.polytechnic.touristo_app.models.story_model;
import com.polytechnic.touristo_app.models.value_set_get;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InsideHotPlacesAdapter extends RecyclerView.Adapter<InsideHotPlacesAdapter.viewholder>{
    ArrayList<story_model> list;
    Context con;


    public InsideHotPlacesAdapter(ArrayList<story_model> list, Context con) {
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
        final story_model temp = list.get(position);
        story_model v = list.get(position);

        holder.t1.setText(v.getName());
        holder.t2.setText("₹" + String.valueOf(v.getPrice()));
        holder.t3.setText(String.valueOf(v.getLikes()));

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


    public class viewholder extends RecyclerView.ViewHolder {
        ImageView i;
        TextView t1,t2,t3;
        CardView cardView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            //calls freg
            i= itemView.findViewById(R.id.cv_hotels_img);
            t1= itemView.findViewById(R.id.location_name);
            t2= itemView.findViewById(R.id.tv_price);
            t3= itemView.findViewById(R.id.tv_likesCount);
            cardView = itemView.findViewById(R.id.home_touristo_cardView);

        }


    }




}



