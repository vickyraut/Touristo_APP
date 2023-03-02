package com.polytechnic.touristo_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polytechnic.touristo_app.adapters.rec_foryou_adapter;
import com.polytechnic.touristo_app.adapters.stories_adapters;
import com.polytechnic.touristo_app.models.rec_for_you_model;
import com.polytechnic.touristo_app.models.stories_model;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
//
//
//        View decorView = view.getWindow().getDecorView();
//        // Hide the status bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);


        TextView seeAllForyou = view.findViewById(R.id.Tv_seeAll);
        @SuppressLint("CutPasteId") TextView seeAllStories = view.findViewById(R.id.Tv_seeAll);

        seeAllForyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SeeAll_GL.class);
                startActivity(i);
            }
        });

        seeAllStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getContext(), SeeAll_GL.class);
                startActivity(i1);
            }
        });


        ArrayList<rec_for_you_model> list = new ArrayList<>();
        ArrayList<stories_model> list2 = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.rec_foryou);
        RecyclerView recyclerView2 = view.findViewById(R.id.rec_stories);

        list.add(new rec_for_you_model(R.drawable.reg_bg, R.drawable.ic_loc, "Hiroshima Historic kyoto", "Japan"));
        list.add(new rec_for_you_model(R.drawable.reg_bg, R.drawable.ic_loc, "Hiroshima Historic kyoto", "Japan"));
        list.add(new rec_for_you_model(R.drawable.reg_bg, R.drawable.ic_loc, "Hiroshima Historic kyoto", "Japan"));
        list.add(new rec_for_you_model(R.drawable.reg_bg, R.drawable.ic_loc, "Hiroshima Historic kyoto", "Japan"));
        list2.add(new stories_model(R.drawable.reg_bg));
        list2.add(new stories_model(R.drawable.reg_bg));
        list2.add(new stories_model(R.drawable.reg_bg));
        list2.add(new stories_model(R.drawable.reg_bg));
        list2.add(new stories_model(R.drawable.reg_bg));


        rec_foryou_adapter adapter = new rec_foryou_adapter(list, getContext());
        recyclerView.setAdapter(adapter);

        stories_adapters adapter2 = new stories_adapters(list2, getContext());
        recyclerView2.setAdapter(adapter2);

        LinearLayoutManager Manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(Manager);

        LinearLayoutManager Manager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView2.setLayoutManager(Manager2);




        return view;
    }
}