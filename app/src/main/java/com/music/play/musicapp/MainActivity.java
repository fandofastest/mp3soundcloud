package com.music.play.musicapp;

import android.Manifest;
import android.graphics.Color;
import android.graphics.Typeface;

import com.google.android.material.tabs.TabLayout;


import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;


import Adapter.CategoryAdapter;
import guy4444.smartrate.SmartRate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    SearchView searchView;
    TabLayout tabs;
    ViewPager viewPager;
    CategoryAdapter categoryAdapter;
    LinearLayout linear;
    ImageView play,pause,imgsearch;
    private Typeface mTypeface;
    public static String querysearch;
    LinearLayout progresly;

    FragmentRefreshListener fragmentRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = findViewById(R.id.viewpager);
        linear = findViewById(R.id.linear);
        searchView=findViewById(R.id.searchview);
        imgsearch=findViewById(R.id.ic_search);
        progresly=findViewById(R.id.llProgressBar);




        categoryAdapter = new CategoryAdapter(getSupportFragmentManager());
        viewPager.setAdapter(categoryAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);

        play= findViewById(R.id.play);
        pause= findViewById(R.id.pause);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
            } else {
            }
        } else {
        }

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });





        mTypeface = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Medium.ttf");
        ViewGroup vg = (ViewGroup) tabs.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
                }
            }
        }

    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.play:

                pause.setVisibility(View.VISIBLE);
                play.setVisibility(View.GONE);


                break;

            case R.id.pause:

                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                break;

        }


    }





    public  void showloading(){
        progresly.setVisibility(View.VISIBLE);
    }

    public  void hideoading(){
        progresly.setVisibility(View.GONE);
    }

    public  void onexit(){


    }

    @Override
    public void onBackPressed() {
        SmartRate.Rate(MainActivity.this
                , "Rate Us"
                , "Tell others what you think about this app"
                , "Continue"
                , "Please take a moment and rate us on Google Play"
                , "click here"
                , "Cancel"
                , "Thanks for the feedback"
                , Color.parseColor("#FB4093")
                , 4
                , new SmartRate.CallBack_UserRating() {
                    @Override
                    public void userRating(int rating) {
                        // Do something
                        // maybe from now disable this button
                    }
                }
        );


    }
}
