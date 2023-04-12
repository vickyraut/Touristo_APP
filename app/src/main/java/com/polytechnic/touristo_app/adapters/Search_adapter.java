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
import com.polytechnic.touristo_app.models.Exp_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Search_adapter extends RecyclerView.Adapter<Search_adapter.ExpViewHolder> {

    Context con;
    private List<Exp_Model> mList = new ArrayList<>();

    public Search_adapter(List<Exp_Model> mList, Context con) {
        this.mList = mList;
        this.con = con;
    }

    @NonNull
    @Override
    public Search_adapter.ExpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler, parent, false);
        return new Search_adapter.ExpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_adapter.ExpViewHolder holder, int position) {

        final Exp_Model temp = mList.get(position);

        Exp_Model v = mList.get(position);
        holder.location_name.setText(v.getName());
        String price = "$" + String.valueOf(v.getPrice());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.slide_out_bottom));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, SelectedLocationActivity.class);
                intent.putExtra("name",temp.getName());
                intent.putExtra("image",temp.getImage());
                intent.putExtra("price", temp.getPrice());
                intent.putExtra("days",temp.getDays());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                con.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ExpViewHolder extends RecyclerView.ViewHolder {
        TextView location_name;
        CardView cardView;

        public ExpViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.search_cardView);
            location_name = itemView.findViewById(R.id.search_locationName);
        }
    }
}
