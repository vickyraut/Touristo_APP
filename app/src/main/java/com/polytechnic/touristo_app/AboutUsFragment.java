package com.polytechnic.touristo_app;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class AboutUsFragment extends Fragment {

    public static String msg;
    public static String monum="9146816519" ;
    public AboutUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_about_us, container, false);
        EditText message;
        Button send_msg;



        message=view.findViewById(R.id.et_msg);
        send_msg=view.findViewById(R.id.btn_send);
        msg=message.getText().toString();

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){

                        sendSms();
                    }else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);

                    }
                }
            }
        });


        return view;
    }
    private void sendSms(){

        //mobile_no.getText().toString();

        //"9921908795";
        try{
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(monum,null,msg,null,null);
            Toast.makeText(getContext(), "message sent successfully", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "failed to sent message", Toast.LENGTH_SHORT).show();

        }
    }
}