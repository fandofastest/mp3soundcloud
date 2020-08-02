package com.music.free.musicapp;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import ModalClass.SongModalClass;

import static android.widget.Toast.LENGTH_SHORT;
import static com.music.free.musicapp.MainActivity.totalduration;

/**
 * Created by Remmss on 29-08-2017.
 */

public class PlayerActivity extends AppCompatActivity implements CommonFragment.onSomeEventListener,View.OnClickListener {


    ImageView img_play, img_pause;
    int pos=0;
    ProgressBar progressBar;
    TextView tvtitle, tvartist, tv_song_current_duration, tv_song_total_duration;
    ImageView imageView,next,prev;
    SeekBar seekBar;
    int currentpost=0;
    int newpost;

    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_sliding_view);

        if (Splash_activity.statususer.equals("aman")) {

            LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String status = intent.getStringExtra("status");

                    if (status.equals("playing")) {


                        playMediaPlayer();


                    } else if (status.equals("stoping")) {
                        pauseMediaPlayer();
                    }

                }


            }, new IntentFilter("fando"));


            img_play = (ImageView) findViewById(R.id.img_play);
            img_pause = (ImageView) findViewById(R.id.img_pause);
            next=findViewById(R.id.nextview);
            prev=findViewById(R.id.prevview);

            progressBar = findViewById(R.id.progressBar);
            seekBar = findViewById(R.id.seekbar);

            // set Progress bar values
            seekBar.setProgress(0);

            seekBar.setMax(Utils.MAX_PROGRESS);


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar1, int progress, boolean b) {

                    if(b){

                        seekBar.setProgress(progress);
                            updateseekbarmp(progress);

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });



            tv_song_current_duration = (TextView) findViewById(R.id.tvmin);
            tv_song_total_duration = (TextView) findViewById(R.id.tvmax);

            img_play.setOnClickListener(this);
            img_pause.setOnClickListener(this);
            next.setOnClickListener(this);
            prev.setOnClickListener(this);
            tvartist = findViewById(R.id.artist);
            tvtitle = findViewById(R.id.title);
            imageView = findViewById(R.id.imagefoto);

            pos=getIntent().getIntExtra("pos",0);

            tvtitle.setText("Please Wait Preparing Your Music");
            tvartist.setText("");

            playmusic(pos);




            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" )));

        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.img_play:
               playMediaPlayer();
                break;

            case R.id.img_pause:
                pauseMediaPlayer();
                break;
            case R.id.nextview:
                next();
                System.out.println("nexxx");
                break;
            case R.id.prevview:
               prev();
                break;

        }

    }

    public void playmusic(int pos) {
        currentpost=pos;

        SongModalClass songModalClass = SongsFragment.listsongModalClasses.get(pos);
        tvartist.setText(songModalClass.getArtistName());
        tvtitle.setText(songModalClass.getSongName());

        Glide.with(getApplicationContext()).load(songModalClass.getImgurl()).error(R.drawable.icon).into(imageView);



        Intent plyerservice = new Intent(PlayerActivity.this, MediaPlayerService.class);

        plyerservice.putExtra("mediaurl", Constants.SERVERURL + songModalClass.getId());


        startService(plyerservice);


    }

    @Override
    public void someEvent(int s) {

    }


    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            updateTimerAndSeekbar();
            // Running this thread after 10 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    private void updateTimerAndSeekbar() {


        Intent intent = new Intent("fando");
        intent.putExtra("status", "getduration");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);


        // Displaying Total Duration time
        Utils utils = new Utils();
        tv_song_total_duration.setText(utils.milliSecondsToTimer(totalduration));
        // Displaying time completed playing
        tv_song_current_duration.setText(utils.milliSecondsToTimer(MainActivity.currentduraiton));

        // Updating progress bar
        int progress = (int) (utils.getProgressSeekBar(MainActivity.currentduraiton, totalduration));
        seekBar.setProgress(progress);
    }

    // stop player when destroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdateTimeTask);
    }


    public void next(){

        if (currentpost==SongsFragment.listsongModalClasses.size()-1){
            newpost= 0;

        }
        else {
            newpost=currentpost+1;
        }

        playmusic(newpost);

    }

    public void prev(){



        if (currentpost==0){
              newpost= SongsFragment.listsongModalClasses.size()-1;

        }
        else {
            newpost=currentpost-1;
        }

        playmusic(newpost);



    }

    public void pauseMediaPlayer() {
        img_play.setVisibility(View.VISIBLE);
        img_pause.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        Intent intent = new Intent("fando");
        intent.putExtra("status", "pause");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void playMediaPlayer() {

        Intent intent = new Intent("fando");
        intent.putExtra("status", "resume");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        progressBar.setVisibility(View.GONE);
        img_pause.setVisibility(View.VISIBLE);
        img_play.setVisibility(View.GONE);

        // Changing button image to pause button
        mHandler.post(mUpdateTimeTask);
    }

    public void  updateseekbarmp(int progress){

        double currentseek = ((double) progress/(double)Utils.MAX_PROGRESS);

        int totaldura= (int) totalduration;
        int seek= (int) (totaldura*currentseek);

        Intent intent = new Intent("fando");
        intent.putExtra("status", "seek");
        intent.putExtra("seektime",seek);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);




        System.out.println("sekarang : "+seek);
//        System.out.println("sekarang pro "+progress);


    }
}