package com.polytechnic.touristo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Edit_ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText et_firstname = findViewById(R.id.edt_et_first);
        EditText et_lastname = findViewById(R.id.edt_et_last);
        EditText et_email = findViewById(R.id.edt_et_email);
        EditText et_password = findViewById(R.id.edt_et_pass);
        EditText et_Address = findViewById(R.id.edt_et_Address);
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
    }
}