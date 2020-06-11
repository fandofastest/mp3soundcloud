package com.music.free.musicapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import Adapter.CustomPageAdapter;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Remmss on 29-08-2017.
 */

public class PlayerActivity extends AppCompatActivity implements CommonFragment.onSomeEventListener,View.OnClickListener {


    private ViewPager viewPager;
    private ImageView iv_blur;
    CustomPageAdapter customPageAdapter;
    ImageView img_play,img_pause;
    String id,duration,title,imgurl;
    MediaPlayer mp;
    ProgressBar progressBar;
    TextView tvtitle,tvartist,tv_song_current_duration,tv_song_total_duration;
     ImageView imageView;
     SeekBar seekBar;

    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();



    int[] mResources = {
            R.drawable.ic_gray,
            R.drawable.ic_gray,
            R.drawable.ic_gray,
            R.drawable.ic_gray,
            R.drawable.ic_gray,
            R.drawable.ic_gray
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_sliding_view);

        if (Splash_activity.statususer.equals("aman")) {


            img_play = (ImageView) findViewById(R.id.img_play);
            img_pause = (ImageView) findViewById(R.id.img_pause);
            progressBar = findViewById(R.id.progressBar);
            seekBar = findViewById(R.id.seekbar);
            // set Progress bar values
            seekBar.setProgress(0);

            seekBar.setMax(MusicUtils.MAX_PROGRESS);

            tv_song_current_duration = (TextView) findViewById(R.id.tvmin);
            tv_song_total_duration = (TextView) findViewById(R.id.tvmax);

            img_play.setOnClickListener(this);
            img_pause.setOnClickListener(this);
            tvartist = findViewById(R.id.artist);
            tvtitle = findViewById(R.id.title);
            imageView = findViewById(R.id.imagefoto);

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            imgurl = getIntent().getStringExtra("imgurl");
            duration=getIntent().getStringExtra("duration");
            playmusic(id);


            tvtitle.setText("Please Wait Preparing Your Music");
            tvartist.setText("");
            Glide.with(getApplicationContext()).load(imgurl).error(R.drawable.icon).into(imageView);


            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        else{
            id = getIntent().getStringExtra("id");
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+id)));

        }

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.img_play:
                mp.start();
                img_pause.setVisibility(View.VISIBLE);
                img_play.setVisibility(View.GONE);
                mHandler.post(mUpdateTimeTask);
                break;

            case R.id.img_pause:
                mp.pause();
                img_play.setVisibility(View.VISIBLE);
                img_pause.setVisibility(View.GONE);
                break;

        }

    }

    public void playmusic(String id){

        // Media PlayerActivity
        mp = new MediaPlayer();
        mp.stop();
        mp.reset();
        mp.release();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Changing button image to play button
//                bt_play.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            }
        });

        try {
            Uri myUri = Uri.parse(Constants.SERVERURL+id+"&key="+Splash_activity.key);
            mp = new MediaPlayer();
            mp.setDataSource(this, myUri);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepareAsync(); //don't use prepareAsync for mp3 playback
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot load audio file", LENGTH_SHORT).show();

        }

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onPrepared(MediaPlayer mplayer) {
                if (mp.isPlaying()) {
                    mp.pause();
                    img_play.setVisibility(View.VISIBLE);
                    img_pause.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    // Changing button image to play button

                } else {
                    // Resume song
                    tvartist.setText("artist");
                    tvtitle.setText(title);
                    progressBar.setVisibility(View.GONE);
                    img_pause.setVisibility(View.VISIBLE);
                    img_play.setVisibility(View.GONE);
                    mp.start();
                    // Changing button image to pause button
                    mHandler.post(mUpdateTimeTask);
                }

            }
        });

        mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {


            }
        });
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
            if (mp.isPlaying()) {
                mHandler.postDelayed(this, 100);
            }
        }
    };

    private void updateTimerAndSeekbar() {
        long totalDuration = mp.getDuration();
        long currentDuration = mp.getCurrentPosition();

        // Displaying Total Duration time
        MusicUtils utils = new MusicUtils();
        tv_song_total_duration.setText(utils.milliSecondsToTimer(totalDuration));
        // Displaying time completed playing
        tv_song_current_duration.setText(utils.milliSecondsToTimer(currentDuration));

        // Updating progress bar
        int progress = (int) (utils.getProgressSeekBar(currentDuration, totalDuration));
        seekBar.setProgress(progress);
    }

    // stop player when destroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdateTimeTask);
        mp.release();
    }


    public class MusicUtils {

        public static final int MAX_PROGRESS = 10000;

        /**
         * Function to convert milliseconds time to
         * Timer Format
         * Hours:Minutes:Seconds
         */
        public String milliSecondsToTimer(long milliseconds) {
            String finalTimerString = "";
            String secondsString = "";

            // Convert total duration into time
            int hours = (int) (milliseconds / (1000 * 60 * 60));
            int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
            int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
            // Add hours if there
            if (hours > 0) {
                finalTimerString = hours + ":";
            }

            // Prepending 0 to seconds if it is one digit
            if (seconds < 10) {
                secondsString = "0" + seconds;
            } else {
                secondsString = "" + seconds;
            }

            finalTimerString = finalTimerString + minutes + ":" + secondsString;

            // return timer string
            return finalTimerString;
        }

        /**
         * Function to get Progress percentage
         *
         * @param currentDuration
         * @param totalDuration
         */
        public int getProgressSeekBar(long currentDuration, long totalDuration) {
            Double progress = (double) 0;
            // calculating percentage
            progress = (((double) currentDuration) / totalDuration) * MAX_PROGRESS;

            // return percentage
            return progress.intValue();
        }

        /**
         * Function to change progress to timer
         *
         * @param progress - totalDuration
         *                 returns current duration in milliseconds
         */
        public int progressToTimer(int progress, int totalDuration) {
            int currentDuration = 0;
            totalDuration = (int) (totalDuration / 1000);
            currentDuration = (int) ((((double) progress) / MAX_PROGRESS) * totalDuration);

            // return current duration in milliseconds
            return currentDuration * 1000;
        }

    }

    public void pauseMediaPlayer() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
            }

        }
    }

    public void playMediaPlayer() {
        if (mp != null) {
            mp.start();

        }
    }
}
