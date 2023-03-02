package com.polytechnic.touristo_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.widget.PopupMenu;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Home extends AppCompatActivity {

    private SmoothBottomBar smoothBottomBar;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        replace(new HomeFragment());
//        setupNavigationComponent();


//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        smoothBottomBar= findViewById(R.id.bottomBar);

      smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
          @Override
          public boolean onItemSelect(int i) {
              switch (i){
                  case 0:
                      replace(new HomeFragment());
                      break;
                  case 1:
                      replace(new ExploreFragment());
                      break;
                  case 2:
                      replace(new LikedFragment());
                      break;
                  case 3:
                      replace(new ProfileFragment());
                      break;
              }
              return false;
          }
      });


    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame,fragment);
        transaction.commit();

    }
//
//    private void setupNavigationComponent() {
//
//            navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//            navController= navHostFragment.getNavController();
//            setupSmoothBottomMenu();
//    }
//
//    private void setupSmoothBottomMenu() {
//        PopupMenu popupMenu= new PopupMenu(this,null);
//        popupMenu.inflate(R.menu.navmenu);
//        Menu menu=popupMenu.getMenu();
//
//        activityMainBinding.bottomNavigation
//    }

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