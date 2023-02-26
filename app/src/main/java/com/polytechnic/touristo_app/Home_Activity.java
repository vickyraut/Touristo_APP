package com.polytechnic.touristo_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.polytechnic.touristo_app.adapters.rec_foryou_adapter;
import com.polytechnic.touristo_app.adapters.stories_adapters;
import com.polytechnic.touristo_app.models.rec_for_you_model;
import com.polytechnic.touristo_app.models.stories_model;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Home_Activity extends AppCompatActivity {

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        TextView seeAllForyou = findViewById(R.id.Tv_seeAll);
        @SuppressLint("CutPasteId") TextView seeAllStories = findViewById(R.id.Tv_seeAll);

        seeAllForyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home_Activity.this, SeeAll_GL.class);
                startActivity(i);
            }
        });

        seeAllStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Home_Activity.this, SeeAll_GL.class);
                startActivity(i1);
            }
        });


        ArrayList<rec_for_you_model> list = new ArrayList<>();
        ArrayList<stories_model> list2 = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rec_foryou);
        RecyclerView recyclerView2 = findViewById(R.id.rec_stories);

        list.add(new rec_for_you_model(R.drawable.reg_bg, R.drawable.ic_loc, "Hiroshima Historic kyoto", "Japan"));
        list.add(new rec_for_you_model(R.drawable.reg_bg, R.drawable.ic_loc, "Hiroshima Historic kyoto", "Japan"));
        list.add(new rec_for_you_model(R.drawable.reg_bg, R.drawable.ic_loc, "Hiroshima Historic kyoto", "Japan"));
        list.add(new rec_for_you_model(R.drawable.reg_bg, R.drawable.ic_loc, "Hiroshima Historic kyoto", "Japan"));
        list2.add(new stories_model(R.drawable.reg_bg));
        list2.add(new stories_model(R.drawable.reg_bg));
        list2.add(new stories_model(R.drawable.reg_bg));
        list2.add(new stories_model(R.drawable.reg_bg));
        list2.add(new stories_model(R.drawable.reg_bg));


        rec_foryou_adapter adapter = new rec_foryou_adapter(list, Home_Activity.this);
        recyclerView.setAdapter(adapter);

        stories_adapters adapter2 = new stories_adapters(list2, Home_Activity.this);
        recyclerView2.setAdapter(adapter2);

        LinearLayoutManager Manager = new LinearLayoutManager(Home_Activity.this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(Manager);

        LinearLayoutManager Manager2 = new LinearLayoutManager(Home_Activity.this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView2.setLayoutManager(Manager2);
    }

}


//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager2.widget.CompositePageTransformer;
//import androidx.viewpager2.widget.MarginPageTransformer;
//import androidx.viewpager2.widget.ViewPager2;
//
//import android.os.Bundle;
//import android.view.View;
//
//import com.polytechnic.touristo_app.models.value_set_get;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Home_Activity extends AppCompatActivity {
//    ArrayList<value_set_get> list = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        ViewPager2 locationsViewPager = findViewById(R.id.locationsViewPager);
//
//        List<TravelLoacation> travelLoacations = new ArrayList<>();
//
//        TravelLoacation travelLocationiconEiffelTower = new TravelLoacation();
//        travelLocationiconEiffelTower.imageUrl = "https://unsplash.com/photos/eU4pipU_8HA";
//        travelLocationiconEiffelTower.title = "France";
//        travelLocationiconEiffelTower.location = "Eiffel tower";
//        travelLocationiconEiffelTower.starRating = 4.8f;
//        travelLoacations.add(travelLocationiconEiffelTower);
//
//
//        TravelLoacation travelLocationiconTaj = new TravelLoacation();
//        travelLocationiconEiffelTower.imageUrl = "https://unsplash.com/photos/eU4pipU_8HA";
//        travelLocationiconEiffelTower.title = "France";
//        travelLocationiconEiffelTower.location = "Eiffel tower";
//        travelLocationiconEiffelTower.starRating = 4.8f;
//        travelLoacations.add(travelLocationiconTaj);
//
//        locationsViewPager.setAdapter(new TravelLocationAdapter(travelLoacations));
//
//        locationsViewPager.setClipToPadding(false);
//        locationsViewPager.setClipChildren(false);
//        locationsViewPager.setOffscreenPageLimit(3);
//        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
//
//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
//        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1 - Math.abs(position);
//                page.setScaleY(0.95f + r * 0.05f);
//
//            }
//        });
//
//
//    }
//}