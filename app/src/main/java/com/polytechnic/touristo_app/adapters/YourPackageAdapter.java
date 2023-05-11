package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.models.YourPackageModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YourPackageAdapter extends RecyclerView.Adapter<YourPackageAdapter.YPViewHolder> {
    Context con;
    private List<YourPackageModel> list;

    public YourPackageAdapter(Context con, List<YourPackageModel> list) {
        this.con = con;
        this.list = list;
    }

    @NonNull
    @Override
    public YourPackageAdapter.YPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_packages_rec, parent, false);
        return new YourPackageAdapter.YPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourPackageAdapter.YPViewHolder holder, int position) {
        final YourPackageModel v = list.get(position);

        if (v.getImage().isEmpty()){
            holder.imageView.setImageResource(R.drawable.reg_bg);
        }else {
            Picasso.get()
                    .load(v.getImage())
                    .error(R.drawable.reg_bg)
                    .into(holder.imageView);
        }

        holder.t1.setText(v.getName());
        holder.t2.setText(v.getTime());
        holder.t3.setText(v.getDate());
        holder.t4.setText(v.getMembers());
        holder.t5.setText(v.getTrac());
        holder.t6.setText(v.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class YPViewHolder  extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView t1, t2, t3, t4, t5, t6;
        public YPViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_yourpackage_place_image);
            t1 = itemView.findViewById(R.id.tv_yourpackage_place_name);
            t2 = itemView.findViewById(R.id.tv_ypr_timetv);
            t3 = itemView.findViewById(R.id.tv_yourpackage_date);
            t4 = itemView.findViewById(R.id.tv_yourpackage_persons);
            t5 = itemView.findViewById(R.id.tv_your_package_refund);
            t6 = itemView.findViewById(R.id.tv_yourpackage_total_price);
        }
    }
}
