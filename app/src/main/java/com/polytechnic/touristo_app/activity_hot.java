package com.polytechnic.touristo_app;

import static android.widget.LinearLayout.HORIZONTAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.polytechnic.touristo_app.adapters.saved_rec_adapter;
import com.polytechnic.touristo_app.adapters.story_adapter;
import com.polytechnic.touristo_app.models.saved_rec_model;
import com.polytechnic.touristo_app.models.story_model;

import java.util.ArrayList;

public class activity_hot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_hot);

        RecyclerView recyclerV= findViewById(R.id.recv_story);

        ArrayList<story_model> story_list=new ArrayList<>();
        story_list.add(new story_model(R.drawable.img));
        story_list.add(new story_model(R.drawable.taj1));
        story_list.add(new story_model(R.drawable.img));
        story_list.add(new story_model(R.drawable.taj1));
        story_list.add(new story_model(R.drawable.img));
        story_list.add(new story_model(R.drawable.taj1));



        story_adapter adapter= new story_adapter(story_list,this);
        recyclerV.setAdapter(adapter);


        LinearLayoutManager manager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerV.setLayoutManager(manager);

    }
}