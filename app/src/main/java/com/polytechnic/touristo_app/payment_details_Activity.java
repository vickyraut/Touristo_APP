package com.polytechnic.touristo_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Calendar;
import java.util.Date;

public class payment_details_Activity extends AppCompatActivity {

    EditText et_email;
    EditText et_phone;
    EditText et_fullName;
    TextView tv_time;
    TextView tv_date;
    Spinner act_members;
    AppCompatButton btn_next, btn_time, btn_date;
    String email, phone, fullName;
    String day_date = "";
    Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Calendar calendar = Calendar.getInstance();
    Date currentDate = new Date();
    Date bookingDate;
    private int mDay, mMonth, mYear, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        et_email = findViewById(R.id.et_pd_email);
        et_phone = findViewById(R.id.et_pd_phone_no);
        et_fullName = findViewById(R.id.et_pd_full_name);
        tv_time = findViewById(R.id.tv_pd_time);
        tv_date = findViewById(R.id.dp_pd_date);
        act_members = findViewById(R.id.act_Item_pd_members);
        btn_next = findViewById(R.id.btn_pd_next);
        btn_time = findViewById(R.id.btn_select_time);
        btn_date = findViewById(R.id.btn_select_date);

        email = preferences.getString("Useremail", "Enter Email");
        phone = et_phone.getText().toString();
        fullName = preferences.getString("userFullName", "Enter Name");

        et_email.setText(email);
        et_fullName.setText(fullName);
        tv_time.setText(" ");
        tv_date.setText(" ");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arr);
        act_members.setAdapter(arrayAdapter);

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_email.getText().toString().isEmpty()) {
                    et_email.setError("Please kindly enter Email");
                } else if (et_phone.getText().toString().isEmpty()) {
                    et_phone.setError("Please kindly enter Phone");
                } else if (et_fullName.getText().toString().isEmpty()) {
                    et_fullName.setError("Please kindly enter Name");
                } else if (!(et_email.getText().toString().contains("@")) && !(et_email.getText().toString().contains(".com"))) {
                    et_email.setError("Please kindly enter valid Email");
                } else if ((et_phone.getText().toString().length()) < 10) {
                    et_phone.setError("Please kindly enter valid Phone");
                } else if (bookingDate == null) {
                    Toast.makeText(payment_details_Activity.this, "Please Enter Date", Toast.LENGTH_SHORT).show();
                } else if (currentDate.getDate() == bookingDate.getDate()) {
                    Toast.makeText(payment_details_Activity.this, "Ypo have to book at-least day before", Toast.LENGTH_SHORT).show();
                } else if (currentDate.after(bookingDate)) {
                    Toast.makeText(payment_details_Activity.this, "Please select valid Date", Toast.LENGTH_SHORT).show();
                } else {
                    getDay_date();
                    Intent intent = new Intent(payment_details_Activity.this, PaymentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name", getIntent().getStringExtra("name"));
                    intent.putExtra("country", getIntent().getStringExtra("country"));
                    intent.putExtra("city", getIntent().getStringExtra("city"));
                    intent.putExtra("image", getIntent().getStringExtra("image"));
                    intent.putExtra("price", getIntent().getIntExtra("price", 0));
                    intent.putExtra("days", getIntent().getStringExtra("days"));
                    intent.putExtra("time", tv_time.getText().toString());
                    intent.putExtra("day_date", day_date);
                    intent.putExtra("member", act_members.getSelectedItem().toString());
                    startActivity(intent);
                }
            }

        });

    }

    private void getDay_date() {
        int booking_day = bookingDate.getDay();
        switch (booking_day) {
            case 0:
                day_date = day_date + "Sunday";
                break;
            case 1:
                day_date = day_date + "Monday";
                break;
            case 2:
                day_date = day_date + "Tuesday";
                break;
            case 3:
                day_date = day_date + "Wednesday";
                break;
            case 4:
                day_date = day_date + "Thursday";
                break;
            case 5:
                day_date = day_date + "Friday";
                break;
            case 6:
                day_date = day_date + "Saturday";
                break;

        }

        day_date = day_date + ", " + String.valueOf(bookingDate.getDate()) + " ";

        int booking_month = bookingDate.getMonth();

        switch (booking_month) {
            case 0:
                day_date = day_date + "January";
                break;
            case 1:
                day_date = day_date + "February";
                break;
            case 2:
                day_date = day_date + "March";
                break;
            case 3:
                day_date = day_date + "April";
                break;
            case 4:
                day_date = day_date + "May";
                break;
            case 5:
                day_date = day_date + "June";
                break;
            case 6:
                day_date = day_date + "July";
                break;
            case 7:
                day_date = day_date + "August";
                break;
            case 8:
                day_date = day_date + "September";
                break;
            case 9:
                day_date = day_date + "October";
                break;
            case 10:
                day_date = day_date + "November";
                break;
            case 11:
                day_date = day_date + "December";
                break;
        }

        day_date = day_date + " " + String.valueOf(bookingDate.getYear()-100);


    }

    private void setTime() {
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(payment_details_Activity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay > 12) {
                    tv_time.setText(hourOfDay + ":" + minute + " PM");
                } else {
                    tv_time.setText(hourOfDay + ":" + minute + " PM");
                }
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void setDate() {
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(payment_details_Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                bookingDate = new Date(year - 1900, month, dayOfMonth);
                tv_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                Log.d("date", "cu: " + String.valueOf(currentDate));
                Log.d("date", "bo: " + String.valueOf(bookingDate));
                Log.d("date", String.valueOf(currentDate.after(bookingDate)));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

}