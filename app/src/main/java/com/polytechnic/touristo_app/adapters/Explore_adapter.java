package com.polytechnic.touristo_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.Login_Activity;
import com.polytechnic.touristo_app.R;
import com.polytechnic.touristo_app.SelectedLocationActivity;
import com.polytechnic.touristo_app.SignUP_Activity;
import com.polytechnic.touristo_app.models.Exp_Model;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Explore_adapter extends RecyclerView.Adapter<Explore_adapter.ExpViewHolder> {

    Context con;
    private List<Exp_Model> mList = new ArrayList<>();
    int switchLiked, id;

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

        if (v.getImage().isEmpty()){
            holder.imageView.setImageResource(R.drawable.reg_bg);
        }else {
            Picasso.get()
                    .load(v.getImage())
                    .error(R.drawable.reg_bg)
                    .into(holder.imageView);
        }

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim1));

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

        switchLiked = v.isLiked_status();

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
            public void onClick(View v) {
                id = temp.getId();
                if (switchLiked == 1) {
                    holder.liked.setVisibility(View.INVISIBLE);
                    holder.imageView2.setVisibility(View.VISIBLE);
                    switchLiked = 0;
                    setLike();
                } else {
                    holder.imageView2.setVisibility(View.INVISIBLE);
                    holder.liked.setVisibility(View.VISIBLE);
                    holder.liked.setMinAndMaxProgress(0.0f, 0.5f);
                    holder.liked.playAnimation();
                    switchLiked = 1;
                    setLike();
                }
            }
        });

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

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ExpViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, imageView2;
        TextView t, t2, t3,t4;
        CardView cardView;
        LottieAnimationView liked;
        RelativeLayout relativeLayout;

        public ExpViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_exp);
            imageView = itemView.findViewById(R.id.iv_place);
            t = itemView.findViewById(R.id.tv_location_name);
            t2 = itemView.findViewById(R.id.tv_Price);
            t3 = itemView.findViewById(R.id.tv_days);
            liked = itemView.findViewById(R.id.explore_liked);
            imageView2 = itemView.findViewById(R.id.img_liked);
            relativeLayout = itemView.findViewById(R.id.exp_relative);
        }
    }
}
