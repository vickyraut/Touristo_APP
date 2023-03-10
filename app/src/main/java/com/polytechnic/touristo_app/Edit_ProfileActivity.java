package com.polytechnic.touristo_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Edit_ProfileActivity extends AppCompatActivity {

    EditText et_firstname, et_lastname, et_email, et_password, et_Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_firstname = findViewById(R.id.edt_et_first);
        et_lastname = findViewById(R.id.edt_et_last);
        et_email = findViewById(R.id.edt_et_email);
        et_password = findViewById(R.id.edt_et_pass);
        et_Address = findViewById(R.id.edt_et_Address);
        AppCompatButton btn_save = findViewById(R.id.btn_save);


        btn_save.setOnClickListener(new View.OnClickListener() {
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
                                    Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ProfileFragment.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}