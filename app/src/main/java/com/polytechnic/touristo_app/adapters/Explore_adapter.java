package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Explore_adapter extends RecyclerView.Adapter<Explore_adapter.ExpViewHolder> {

    Context con;
    private List<Exp_Model> mList = new ArrayList<>();

    public Explore_adapter(List<Exp_Model> mList, Context con) {
        this.mList = mList;
        this.con = con;
    }

    @NonNull
    @Override
    public Explore_adapter.ExpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_recview, parent, false);
        return new Explore_adapter.ExpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Explore_adapter.ExpViewHolder holder, int position) {

        final Exp_Model temp = mList.get(position);

        Exp_Model v = mList.get(position);
        holder.t.setText(v.getName());
        String price = "$" + String.valueOf(v.getPrice());
        holder.t2.setText(price);
        holder.t3.setText(" /" + v.getDays() + " Days");

        Picasso.get()
                .load(Urls.TouristImageAddress + v.getImage())
                .error(R.drawable.reg_bg)
                .into(holder.imageView);

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
        ImageView imageView;
        TextView t, t2, t3;

        CardView cardView;

        public ExpViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_exp);
            imageView = itemView.findViewById(R.id.iv_place);
            t = itemView.findViewById(R.id.tv_location_name);
            t2 = itemView.findViewById(R.id.tv_Price);
            t3 = itemView.findViewById(R.id.tv_days);
        }
    }
}
