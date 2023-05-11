package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.SelectedLocationActivity;
import com.polytechnic.touristo_app.models.home_touristPlaces_model;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class home_tourist_adapter extends RecyclerView.Adapter<home_tourist_adapter.viewholder> {

    ArrayList<home_touristPlaces_model> list;
    int switchLiked, id;
    Context con;


    public home_tourist_adapter(ArrayList<home_touristPlaces_model> list, Context con) {
        this.list = list;
        this.con = con;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(con).inflate(R.layout.home_tourist_recv,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final home_touristPlaces_model temp = list.get(position);
        home_touristPlaces_model v =list.get(position);

        holder.t1.setText(v.getName());
        holder.t2.setText(String.valueOf(v.getLikes()));

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

        switchLiked = v.getLiked_status();

        if (switchLiked == 0){
            holder.liked.setVisibility(View.INVISIBLE);
            holder.imageView2.setVisibility(View.VISIBLE);
            switchLiked = 1;
        }else {
            holder.imageView2.setVisibility(View.INVISIBLE);
            holder.liked.setVisibility(View.VISIBLE);
            holder.liked.setMinAndMaxProgress(0.0f,0.5f);
            holder.liked.playAnimation();
            switchLiked = 0;
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = temp.getId();
                if (switchLiked == 1) {
                    holder.liked.setVisibility(View.INVISIBLE);
                    holder.imageView2.setVisibility(View.VISIBLE);
                    holder.t2.setText(String.valueOf(v.getLikes()));
                    switchLiked = 0;
                    setLike();
                } else {
                    holder.imageView2.setVisibility(View.INVISIBLE);
                    holder.liked.setVisibility(View.VISIBLE);
                    holder.liked.setMinAndMaxProgress(0.0f, 0.5f);
                    holder.t2.setText(String.valueOf(v.getLikes() + 1));
                    holder.liked.playAnimation();
                    switchLiked = 1;
                    setLike();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        //calls freg
        ImageView i, imageView2;
        TextView t1,t2;
        CardView cardView;
        RelativeLayout relativeLayout;
        LottieAnimationView liked;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            //calls fragment
            i= itemView.findViewById(R.id.cv_hotels_img);
            t1=itemView.findViewById(R.id.tv_hotel_name);
            t2=itemView.findViewById(R.id.tv_likesCount);
            relativeLayout = itemView.findViewById(R.id.htr_relative);
            cardView = itemView.findViewById(R.id.home_touristo_cardView);
            liked = itemView.findViewById(R.id.explore_liked2);
            imageView2 = itemView.findViewById(R.id.img_liked2);

        }

    }

    private void setLike() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Log.d("like","Inside SetLike");
        params.put("id",id);
        params.put("flag", switchLiked);
        Log.d("like",String.valueOf(switchLiked));
        Log.d("like",String.valueOf(id));
        client.post(Urls.urlSetLikes, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String success = response.getString("success");
                    Log.d("like",success);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
