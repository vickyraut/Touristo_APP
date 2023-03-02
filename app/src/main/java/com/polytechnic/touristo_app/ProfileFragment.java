package com.polytechnic.touristo_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileFragment extends Fragment {


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        EditText homg_et_name = view.findViewById(R.id.homg_et_name);
        EditText homg_et_email = view.findViewById(R.id.homg_et_email);
        EditText homg_et_Pass = view.findViewById(R.id.homg_et_Pass);
        EditText homg_et_update_Address = view.findViewById(R.id.homg_et_update_Address);
        EditText homg_et_update_noti = view.findViewById(R.id.homg_et_update_noti);
        EditText homg_et_update_payment = view.findViewById(R.id.homg_et_update_payment);


        ConstraintLayout Cl_your_details = view.findViewById(R.id.Cl_your_details);
        ConstraintLayout Cl_manage_add = view.findViewById(R.id.Cl_manage_add);
        ConstraintLayout Cl_notification = view.findViewById(R.id.Cl_notification);
        ConstraintLayout cl_paymentDetails = view.findViewById(R.id.cl_paymentDetails);

        TextView tv_edit = view.findViewById(R.id.textView20);
        ImageView upload_img = view.findViewById(R.id.imageView13);
        CardView cv_your_details = view.findViewById(R.id.Cv_yourdetails);

        cv_your_details.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int v = (homg_et_name.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(Cl_your_details, new AutoTransition());
                homg_et_name.setVisibility(v);
                homg_et_email.setVisibility(v);
                homg_et_Pass.setVisibility(v);
            }
        });

        Cl_manage_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int v = (homg_et_update_Address.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(Cl_your_details, new AutoTransition());
                homg_et_update_Address.setVisibility(v);
//                homg_et_email.setVisibility(v);
//                homg_et_Pass.setVisibility(v);
            }
        });

        Cl_notification.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int v = (homg_et_update_noti.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(Cl_your_details, new AutoTransition());
                homg_et_update_noti.setVisibility(v);
//                homg_et_email.setVisibility(v);
//                homg_et_Pass.setVisibility(v);
            }
        });

        cl_paymentDetails.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int v = (homg_et_update_payment.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(Cl_your_details, new AutoTransition());
                homg_et_update_payment.setVisibility(v);
//                homg_et_email.setVisibility(v);
//                homg_et_Pass.setVisibility(v);
            }
        });

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getContext(), Edit_ProfileActivity.class);
                startActivity(i1);
            }
        });


        return view;
    }
}