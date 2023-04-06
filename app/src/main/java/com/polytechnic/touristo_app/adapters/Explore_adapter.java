package com.polytechnic.touristo_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.models.Exp_Model;

import java.util.ArrayList;
import java.util.List;

public class Explore_adapter extends RecyclerView.Adapter<Explore_adapter.ExpViewHolder> {

    private List<Exp_Model> mList = new ArrayList<>();

    public Explore_adapter(List<Exp_Model> mList){
        this.mList=mList;
    }
    public class ExpViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView t,t2,t3;
        public ExpViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.iv_place);
            t=itemView.findViewById(R.id.tv_location_name);
            t2=itemView.findViewById(R.id.tv_Price);
            t3=itemView.findViewById(R.id.tv_days);
        }
    }



    @NonNull
    @Override
    public Explore_adapter.ExpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_recview,parent,false);
        return new Explore_adapter.ExpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Explore_adapter.ExpViewHolder holder, int position) {
            Exp_Model v =mList.get(position);
            holder.imageView.setImageResource(v.getIv_place());
            holder.t.setText(v.getName());
            holder.t.setText(v.getPrice());
            holder.t.setText(v.getDays());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
