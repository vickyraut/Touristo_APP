package com.polytechnic.touristo_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class PaymentActivity extends AppCompatActivity implements PaymentStatusListener {

    TextView tv_Place_Name, tv_time, tv_day_date, tv_members, tv_tour_days, tv_booking_fee, tv_subtotal, tv_total;
    ImageView img_location;
    AppCompatButton btn_pay;
    String Place_Name, time, day_date, days, image,tranc;
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

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        String transcId = df.format(c);


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
        tv_total.setText(String.valueOf((float) subtotal + booking_fee));

        if (getIntent().getStringExtra("image").isEmpty()) {
            img_location.setImageResource(R.drawable.reg_bg);
        } else {
            Picasso.get()
                    .load(image)
                    .error(R.drawable.reg_bg)
                    .into(img_location);
        }

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(PaymentActivity.this)
                        .setPayeeVpa("vraut4143@okaxis")
                        .setPayeeName("Vicky Raut from Touristo Corporation")
                        .setPayeeMerchantCode("12345")
                        .setTransactionId(transcId)
                        .setTransactionRefId(transcId)
                        .setDescription("Booking Trip for "+ tv_Place_Name.getText().toString() + " on " + day_date)
                        .setAmount(tv_total.getText().toString());

                try {
                    EasyUpiPayment easyUpiPayment = builder.build();
                    easyUpiPayment.startPayment();
                } catch (AppNotFoundException e) {
                    throw new RuntimeException(e);
                }


            }
        });

    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
        Toast.makeText(this, "Transaction Completed..", Toast.LENGTH_SHORT).show();
        tranc = transactionDetails.getTransactionId();
        updatePackages();
    }

    private void updatePackages() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("image",image);
        params.put("name",tv_Place_Name.getText().toString());
        params.put("time",time);
        params.put("date",day_date);
        params.put("members",tv_members.getText().toString());
        params.put("trac",tranc);
        params.put("price",tv_total.getText().toString());

        client.post(Urls.urlMyTours, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

        });

    }

}