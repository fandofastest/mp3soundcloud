package com.music.play.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Splash_activity extends AppCompatActivity {
    private boolean mIsBackButtonPressed;
    public static String statususer,banner,inter,statusapp,appupdate,q,key="8af436d1e78348ab09f81a99bf43485b";
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);


        getStatusapp(Constants.urlstatus);


    }

    @Override
    public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();

    }


    private void getStatusapp(String url){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
//                    JSONObject jsonObject=response.getJSONObject("status");
                    statususer = response.getString("status");
                    banner = response.getString("banner");
                    inter=response.getString("inter");
                    statusapp=response.getString("statusapp");
                    appupdate=response.getString("appupdate");
                    q=response.getString("q");
                    Button button= findViewById(R.id.buttonstart);
                    ProgressBar progressBar =findViewById(R.id.progressbar);
                    progressBar.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);

                    button.setOnClickListener(view -> showinter());

                    if (statusapp.equals("tewas")){

                        showdialog(appupdate);
                        button.setVisibility(View.GONE);

                    }












                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);


    }


    public  void showinter() {

        Button button= findViewById(R.id.buttonstart);
        ProgressBar progressBar =findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(inter);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                kehome();

                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                kehome();
                // Code to be executed when the interstitial ad is closed.
            }
        });


    }

    public  void kehome(){
        Intent intent = new Intent(Splash_activity.this,MainActivity.class);
        startActivity(intent);
    }

    private void  showdialog(String appupdate){
        new SweetAlertDialog(Splash_activity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("App Was Discontinue")
                .setContentText("Please Install Our New Music App")
                .setConfirmText("Install")

                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog
                                .setTitleText("Install From Playstore")
                                .setContentText("Please Wait, Open Playstore")
                                .setConfirmText("Go")
//                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                                        intent.setData(Uri.parse(
//                                                "https://play.google.com/store/apps/details?id=com.example.android"));
//                                        intent.setPackage("com.android.vending");
//                                        startActivity(intent);
//                                    }
//                                })

                                .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(
                                    "https://play.google.com/store/apps/details?id="+appupdate));
                            intent.setPackage("com.android.vending");
                            startActivity(intent);
//                                Do something after 100ms
                        }, 3000);



                    }
                })
                .show();
    }
}
