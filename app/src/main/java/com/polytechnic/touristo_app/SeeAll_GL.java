package com.polytechnic.touristo_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import  com.polytechnic.touristo_app.adapters.seeAll_glAdapter;
import com.polytechnic.touristo_app.models.seeAll_model;

import java.util.ArrayList;

public class SeeAll_GL extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeall_gl);

        ArrayList<seeAll_model> GridList1 = new ArrayList<>();


        RecyclerView Rv_gl= findViewById(R.id.SALL_rec_gL);

        GridList1.add(new seeAll_model(R.drawable.reg_bg,R.drawable.ic_loc,"Hiroshima Historic kyoto","Japan"));
        GridList1.add(new seeAll_model(R.drawable.reg_bg,R.drawable.ic_loc,"Hiroshima Historic kyoto","Japan"));
        GridList1.add(new seeAll_model(R.drawable.reg_bg,R.drawable.ic_loc,"Hiroshima Historic kyoto","Japan"));
        GridList1.add(new seeAll_model(R.drawable.reg_bg,R.drawable.ic_loc,"Hiroshima Historic kyoto","Japan"));
        GridList1.add(new seeAll_model(R.drawable.reg_bg,R.drawable.ic_loc,"Hiroshima Historic kyoto","Japan"));
        GridList1.add(new seeAll_model(R.drawable.reg_bg,R.drawable.ic_loc,"Hiroshima Historic kyoto","Japan"));
        GridList1.add(new seeAll_model(R.drawable.reg_bg,R.drawable.ic_loc,"Hiroshima Historic kyoto","Japan"));
        GridList1.add(new seeAll_model(R.drawable.reg_bg,R.drawable.ic_loc,"Hiroshima Historic kyoto","Japan"));




        seeAll_glAdapter seeAllAdapter = new seeAll_glAdapter(GridList1 ,SeeAll_GL.this);
        Rv_gl.setAdapter(seeAllAdapter);

        LinearLayoutManager SeeAllManager = new LinearLayoutManager(SeeAll_GL.this,LinearLayoutManager.VERTICAL,false);
        Rv_gl.setLayoutManager(SeeAllManager);


    }
}