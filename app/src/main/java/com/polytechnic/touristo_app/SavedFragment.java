package com.polytechnic.touristo_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polytechnic.touristo_app.adapters.saved_rec_adapter;
import com.polytechnic.touristo_app.models.saved_rec_model;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_saved, container, false);

        RecyclerView recyclerView= view.findViewById(R.id.saved_place_recyclerview);


        GridLayoutManager manager= new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        return view;
    }
}