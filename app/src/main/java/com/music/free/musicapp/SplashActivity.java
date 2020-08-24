package com.music.play.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SplashActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        getStatusApp(Constants.urlstatus);
        getKey();

    }

    @Override
    public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        super.onBackPressed();

    }


    public void getKey(){
        String url="https://fando.id/soundcloud/getapi.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    Constants.setKey(response.replaceAll("^\"|\"$", ""));

                    // Display the first 500 characters of the response string.

                }, error -> {

                });



        Volley.newRequestQueue(SplashActivity.this).add(stringRequest);


    }


    private void getStatusApp(String url){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            try {
                Constants.setBanner(response.getString("banner"));
                Constants.setInter(response.getString("inter"));
                Constants.setStatusapp(response.getString("statusapp"));
                Constants.setAppupdate(response.getString("appupdate"));
                Constants.setQ(response.getString("keyword"));
                Constants.setAds(response.getString("ads"));
                Constants.setBannerfan(response.getString("fanbanner"));
                Constants.setInterfan(response.getString("faninter"));
                Constants.setAppid(response.getString("appid"));
                Constants.setStatususer(response.getString("statususer"));
                Button button= findViewById(R.id.buttonstart);
                ProgressBar progressBar =findViewById(R.id.progressbar);
                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Ads ads = new Ads();
                        if (Constants.getAds().equals("admob")){
                            ads.showinter(SplashActivity.this,Constants.getInter());
                        }
                        else {
                            ads.showinterfb(SplashActivity.this,Constants.getInterfan());

                        }
                        ads.setCustomObjectListener(new Ads.MyCustomObjectListener() {
                            @Override
                            public void onAdsfinish() {
                                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onRewardOk() {

                            }
                        });
                    }
                });

                if (Constants.getStatusapp().equals("suspend")){

                    showDialog(Constants.appupdate);
                    button.setVisibility(View.GONE);

                }












            } catch (JSONException e) {
                Log.e("errr",e.getMessage());
            }


        }, error -> {


            System.out.println("errrrrr"+error.getMessage());

        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);


    }




    private void  showDialog(String appupdate){
        new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("App Was Discontinue")
                .setContentText("Please Install Our New Music App")
                .setConfirmText("Install")

                .setConfirmClickListener(sDialog -> {
                    sDialog
                            .setTitleText("Install From Playstore")
                            .setContentText("Please Wait, Open Playstore")
                            .setConfirmText("Go")


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



                })
                .show();
    }
}
