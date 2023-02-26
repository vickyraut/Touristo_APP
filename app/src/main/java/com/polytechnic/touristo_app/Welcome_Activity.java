package com.polytechnic.touristo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Objects;

public class Welcome_Activity extends AppCompatActivity {

    VideoView vv_background;
    AppCompatButton btn_start;
    String path;
    boolean doubletap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_welcome);

        vv_background = findViewById(R.id.welcome_vid_background);
        btn_start = findViewById(R.id.wecome_start);

        path = "android.resource://com.polytechnic.touristo_app/" + R.raw.welcome_bg;

        Uri uri = Uri.parse(path);
        vv_background.setVideoURI(uri);


        // To start video
        vv_background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setVolume(0f,0f);
            }
        });

        // To play video again and again
        vv_background.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {

        if (doubletap) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press Again to Exit App", Toast.LENGTH_SHORT).show();
            doubletap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap = false;
                }
            }, 2000);
        }

    }
}