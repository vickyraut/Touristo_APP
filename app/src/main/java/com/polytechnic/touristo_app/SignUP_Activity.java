package com.polytechnic.touristo_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.polytechnic.touristo_app.Dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUP_Activity extends AppCompatActivity {
    Button btn_signin;
    TextView tv_logIn;
    ImageView img_back;
    EditText et_email, et_firstname, et_lastname, et_password;
    boolean passwordVisible;
    LoadingDialog loadingDialog = new LoadingDialog(SignUP_Activity.this);
    private String first_name, last_name, email, password;

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

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn_signin.setClickable(false);
                if (et_email.getText().toString().isEmpty()) {
                    et_email.setError("Please Enter Your Email");
                } else if (!et_email.getText().toString().contains("@") || !et_email.getText().toString().contains(".com")) {
                    et_email.setError("Please Enter valid Email Address");
                } else {
                    checkUserAvailibily();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To delete all the spaces from String
                first_name = (et_firstname.getText().toString().trim());
                last_name = (et_lastname.getText().toString().trim());
                email = (et_email.getText().toString().trim());
                password = (et_password.getText().toString().trim());

                if (first_name.isEmpty()) {
                    et_firstname.setError("Please Enter Your firstname");
                } else if (last_name.isEmpty()) {
                    et_lastname.setError("Please Enter Your lastname");
                } else if (email.isEmpty()) {
                    et_email.setError("Please Enter Email");
                } else if (!email.contains("@") || !email.contains(".com")) {
                    et_email.setError("Please Enter valid Email Address");
                } else if (password.isEmpty()) {
                    et_password.setError("Please Enter Your Password");
                } else if (password.length() < 8) {
                    et_password.setError("Password must contains at least 8 Characters");
                } else {
                    loadingDialog.startLoadingDialog();
                    addUserDetails();
                }
            }
        });

        et_password.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= et_password.getRight() - et_password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = et_password.getSelectionEnd();

                        if (passwordVisible) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                et_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            }
                            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                et_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
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

    private void checkUserAvailibily() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("email", et_email.getText().toString());

        client.post(Urls.urlCheckUser, params, new JsonHttpResponseHandler() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String isSuccess = response.getString("success");

                    if (isSuccess.equals("1")) {
                        et_email.setError("Username is Already taken");

                    } else {
                        Drawable drawable = getResources().getDrawable(R.drawable.edittext_background2);
                        et_email.setBackground(drawable);
                        btn_signin.setClickable(true);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(SignUP_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("email", email);
        params.put("firstname", first_name);
        params.put("lastname", last_name);
        params.put("password", password);

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
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUP_Activity.this, Login_Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    loadingDialog.dismissDialog();
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
                        loadingDialog.dismissDialog();
                        Toast.makeText(SignUP_Activity.this, "Server Error, Please try again later...", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}