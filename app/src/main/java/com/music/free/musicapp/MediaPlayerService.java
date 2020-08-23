package com.music.free.musicapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Objects;

public class MediaPlayerService extends Service{


        //player
        private MediaPlayer mp = new MediaPlayer();


        //receiver

        @Override
        public void onCreate() {
            super.onCreate();


            LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String status = intent.getStringExtra(Constants.getStatus());

                    if (status.equals("pause")){
                        mp.pause();
                    }
                    else if (status.equals("resume")){
                        mp.start();

                    }

                    else  if (status.equals("seek")){
                        int seek = intent.getIntExtra("seektime",0);

                        mp.pause();
                        mp.seekTo(seek);
                        mp.start();

                    }
                    else if (status.equals("stopmusic")){
                        mp.release();
                    }
                    else if (status.equals("getduration")){
                     Constants.setTotalduration(mp.getDuration());
                        Constants.setCurrentduration(mp.getCurrentPosition());
                    }




                }
            }, new IntentFilter(Constants.getIntentfilter()));

        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }







        @SuppressLint("WrongConstant")
        @Override
        public int onStartCommand(final Intent intent, int flags, int startId) {

            try {



                String mediaurl = intent.getStringExtra("mediaurl");





                mp.stop();
                mp.reset();
                mp.release();



                Uri myUri = Uri.parse(mediaurl);
                mp = new MediaPlayer();
                mp.setDataSource(this, myUri);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mp.prepareAsync(); //don't use prepareAsync for mp3 playback

                mp.setOnErrorListener((mp, what, extra) -> true);


                mp.setOnCompletionListener(mp1 -> {

                    Intent intent1 = new Intent("fando");
                    intent1.putExtra("status", "stoping");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);

                });



                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onPrepared(MediaPlayer mplayer) {


                        if (mplayer.isPlaying()) {
                            mp.pause();

                        } else {
                            mp.start();
                            Intent intent = new Intent("fando");
                            intent.putExtra("status", "playing");
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);





                        }

                    }


                });





                mp.prepareAsync();


            }
            catch (Exception e){
                Log.println(1,"log", Objects.requireNonNull(e.getMessage()));
            }



            return START_STICKY;
        }









}