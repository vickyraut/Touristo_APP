package com.polytechnic.touristo_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polytechnic.touristo_app.adapters.Explore_adapter;
import com.polytechnic.touristo_app.models.Exp_Model;

import java.util.ArrayList;
import java.util.List;


public class ExploreFragment extends Fragment {

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_explore, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.exp_recview);
        recyclerView.setHasFixedSize(true);

        List<Exp_Model> modelList= new ArrayList<>();
        modelList.add(new Exp_Model(R.drawable.taj1,"Taj mahal","76677K","/6days"));
        modelList.add(new Exp_Model(R.drawable.taj1,"Taj mahal","76677K","/6days"));
        modelList.add(new Exp_Model(R.drawable.taj1,"Taj mahal","76677K","/6days"));
        modelList.add(new Exp_Model(R.drawable.taj1,"Taj mahal","76677K","/6days"));
        modelList.add(new Exp_Model(R.drawable.taj1,"Taj mahal","76677K","/6days"));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        Explore_adapter adapter =new Explore_adapter(modelList);

        recyclerView.setAdapter(adapter);

        return view;
    }
}