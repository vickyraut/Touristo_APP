package com.polytechnic.touristo_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.squareup.picasso.Picasso;

public class PaymentActivity extends AppCompatActivity {

    TextView tv_Place_Name, tv_time, tv_day_date, tv_members, tv_tour_days, tv_booking_fee, tv_subtotal, tv_total;
    ImageView img_location;
    AppCompatButton btn_pay;
    String Place_Name, time, day_date, days, image;
    int members, booking_fee, subtotal, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        tv_Place_Name = findViewById(R.id.tv_ap_place_name);
        tv_time = findViewById(R.id.tv_ap_time);
        tv_day_date = findViewById(R.id.tv_acp_date);
        tv_members = findViewById(R.id.tv_acp_members);
        tv_tour_days = findViewById(R.id.tv_acp_days);
        tv_booking_fee = findViewById(R.id.tv_p_bookingfees_price);
        tv_subtotal = findViewById(R.id.tv_p_subtotal_price);
        tv_total = findViewById(R.id.tv_acp_total_price);
        img_location = findViewById(R.id.iv_ap_placeimg);
        btn_pay = findViewById(R.id.btn_ap_Pay);


        //get Data
        Place_Name = getIntent().getStringExtra("name") + "\n" + getIntent().getStringExtra("city") + ", " + getIntent().getStringExtra("country");
        time = getIntent().getStringExtra("time");
        day_date = getIntent().getStringExtra("day_date");
        days = getIntent().getStringExtra("days");
        members = Integer.parseInt(getIntent().getStringExtra("member"));
        price = getIntent().getIntExtra("price", 0);
        image = getIntent().getStringExtra("image");

        // calculate booking fees
        // 0.05 to get 5% booking margin
        subtotal = price * members;
        booking_fee = (int) (subtotal * 0.05);

//        set data
        tv_Place_Name.setText(Place_Name);
        tv_time.setText(time);
        tv_day_date.setText(day_date);
        tv_members.setText(members + " Adults");
        tv_tour_days.setText(days);
        tv_booking_fee.setText(String.valueOf(booking_fee));
        tv_subtotal.setText(String.valueOf(subtotal));
        tv_total.setText(String.valueOf(subtotal + booking_fee));

        if (getIntent().getStringExtra("image").isEmpty()){
            img_location.setImageResource(R.drawable.reg_bg);
        }else {
            Picasso.get()
                    .load(image)
                    .error(R.drawable.reg_bg)
                    .into(img_location);
        }
    }

}