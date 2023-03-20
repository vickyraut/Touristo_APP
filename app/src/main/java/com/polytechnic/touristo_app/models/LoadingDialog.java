package com.polytechnic.touristo_app.models;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.polytechnic.touristo_app.R;

public class LoadingDialog {

    Activity activity;
    AlertDialog dialog;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    @SuppressLint("InflateParams")
    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dailoge,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(400,400);

    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
