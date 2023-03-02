package com.polytechnic.touristo_app;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.*;

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

import com.polytechnic.touristo_app.Constants.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUP_Activity extends AppCompatActivity {
    Button btn_signin;
    TextView tv_logIn;
    ImageView img_back;
    EditText et_email, et_firstname, et_lastname, et_password;
    boolean passwordVisible;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        btn_signin = findViewById(R.id.signUp_btn_done);
        et_email = findViewById(R.id.signUp_et_email);
        et_firstname = findViewById(R.id.signUp_et_firstname);
        et_lastname = findViewById(R.id.signUp_et_lastname);
        et_password = findViewById(R.id.signUp_et_password);
        tv_logIn = findViewById(R.id.SignUp_tv_logIn);
        img_back = findViewById(R.id.signup_back);


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_email.getText().toString().isEmpty()) {
                    et_email.setError("Please Enter Your Email");
                } else if (!et_email.getText().toString().contains("@") || !et_email.getText().toString().contains(".com")) {
                    et_email.setError("Please Enter valid Email Address");
                } else if (et_firstname.getText().toString().isEmpty()) {
                    et_firstname.setError("Please Enter Your firstname");
                } else if (et_lastname.getText().toString().isEmpty()) {
                    et_lastname.setError("Please Enter Your lastname");
                } else if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("Please Enter Your Password");
                } else if (et_password.getText().toString().length() < 8) {
                    et_password.setError("Password must contains at least 8 Characters");
                } else {
                    addUserDetails();
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
                                et_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            }
                            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                et_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
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


        tv_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUP_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void addUserDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("email", et_email.getText().toString());
        params.put("firstname", et_firstname.getText().toString());
        params.put("lastname", et_lastname.getText().toString());
        params.put("password", et_password.getText().toString());

        client.post(Urls.urlRegisterUser, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            try {
//                           JSONArray jsonArray = new JSONArray(response);
//                           JSONArray jsonArray = new JSONArray(response.getString("success"));
                                String isSuccess = response.getString("success");

                                if (isSuccess.equals("1")) {
                                    Toast.makeText(getApplicationContext(), "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUP_Activity.this, Login_Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUP_Activity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(SignUP_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}