package com.polytechnic.touristo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;


import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Login_Activity extends AppCompatActivity {
    EditText et_email, et_password;
    Button b2;
    ImageView img_back;
    TextView tv_signUp;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        et_email = findViewById(R.id.LogIn_et_email);
        et_password = findViewById(R.id.LogIn_et_password);
        b2 = findViewById(R.id.LogIn_btn_done);
        tv_signUp = findViewById(R.id.LogIn_tv_signup);
        img_back = findViewById(R.id.logIn_back);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_email.getText().toString().isEmpty()) {
                    et_email.setError("Please Enter Email");
                } else if (!et_email.getText().toString().contains("@") || !et_email.getText().toString().contains(".com")) {
                    et_email.setError("Please Enter valid Email Address");
                } else if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("Please Enter Password");
                } else if (et_password.getText().toString().length() < 8) {
                    et_password.setError("Password must contains at least 8 Characters");
                } else {
                    checkUserlogin();
                }
            }

        });

        et_password.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX()>=et_password.getRight()-et_password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=et_password.getSelectionEnd();

                        if (passwordVisible) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                et_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            }
                            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                et_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            }
                            et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;

                        }

                        et_password.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Login_Activity.this, SignUP_Activity.class);
                startActivity(i1);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void checkUserlogin() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("email", et_email.getText().toString());
        params.put("password", et_password.getText().toString());

        client.post(Urls.urlLoginUser, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String issuccess = response.getString("success");

                    if (issuccess.equals("1")) {
                        Toast.makeText(Login_Activity.this, "Login Successfully Done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login_Activity.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login_Activity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Toast.makeText(Login_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}