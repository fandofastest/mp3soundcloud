package Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.krossovochkin.bottomsheetmenu.BottomSheetMenu;
import com.music.play.musicapp.Constants;
import com.music.play.musicapp.PlayerActivity;
import com.music.play.musicapp.R;
import com.music.play.musicapp.Splash_activity;
import com.music.play.musicapp.Utils;

import java.util.List;

import ModalClass.SongModalClass;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.ContentValues.TAG;
import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Remmss on 29-08-2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {

    private List<SongModalClass> songModalClassList;
    public Context ctx;



    public class MyViewHolder extends RecyclerView.ViewHolder  {


        public TextView txt_song_name, txt_artist_name, txt_movie_name,txt_time;
        public ImageView img;

        LinearLayout lyt_layout;


        public MyViewHolder(View view) {
            super(view);

            txt_song_name = (TextView) view.findViewById(R.id.txt_song_name);
            img = (ImageView) view.findViewById(R.id.img);
            txt_artist_name = (TextView) view.findViewById(R.id.txt_artist_name);
            txt_movie_name = (TextView) view.findViewById(R.id.txt_movie_name);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
            lyt_layout=view.findViewById(R.id.lyt_item);



        }


    }

    public SongAdapter(List<SongModalClass> songModalClassList, Context context) {
        this.songModalClassList = songModalClassList;
        this.ctx=context;


    }






    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_songs, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final SongModalClass modalClass = songModalClassList.get(position);
        holder.txt_song_name.setText(modalClass.getSongName());
        Utils utils = new Utils();
        String duratiom= utils.milliSecondsToTimer(Long.parseLong(modalClass.getDuration()));
        holder.txt_time.setText(duratiom);
        holder.txt_movie_name.setVisibility(View.GONE);





//        holder.txt_artist_name.setText(Splash_activity.q);
        holder.txt_artist_name.setText(modalClass.getArtistName());
        Glide
                .with(ctx)
                .load(modalClass.getImgurl())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img);

//        holder.img.setImageResource(modalClass.getImg());
//        holder.txt_artist_name.setText(modalClass.getArtistName());
//        holder.txt_movie_name.setText(modalClass.getMovieName());
//        holder.txt_time.setText(modalClass.getTime());

        holder.lyt_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new BottomSheetMenu.Builder(ctx, new BottomSheetMenu.BottomSheetMenuListener() {
                    @Override
                    public void onCreateBottomSheetMenu(MenuInflater inflater, Menu menu) {
                        inflater.inflate(R.menu.menu, menu);


                    }

                    @Override
                    public void onBottomSheetMenuItemSelected(MenuItem item) {
                        final int itemId = item.getItemId();
                        switch (itemId) {
                            case R.id.play:

                                Intent intent = new Intent(ctx, PlayerActivity.class);
                                intent.putExtra("pos",position);
                                ctx.startActivities(new Intent[]{intent});


                                break;

                            case R.id.dl:

                                SweetAlertDialog pDialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                if (Splash_activity.statususer.equals("aman")) {

                                    pDialog.setTitleText("Loading Your Download");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    long downloadFileRef = downloadFile(Uri.parse(Constants.SERVERURL +modalClass.getId()), Environment.DIRECTORY_DOWNLOADS, modalClass.getSongName()+".mp3");



                                    if (downloadFileRef != 0) {

                                        pDialog.setTitleText("Download Started");
                                        pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


                                    }else {

                                        pDialog.setTitleText("Download Failed");
                                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);


                                    }


                                }else{
                                    pDialog.setTitleText("Not Available");
                                }




                                break;
                        }

                    }
                }).show();





            }
        });





    }





    @Override
    public int getItemCount() {
        return songModalClassList.size();
    }


    private long downloadFile(Uri uri, String fileStorageDestinationUri, String fileName) {

        long downloadReference = 0;

        DownloadManager downloadManager = (DownloadManager)ctx.getSystemService(DOWNLOAD_SERVICE);
        try {
            DownloadManager.Request request = new DownloadManager.Request(uri);

            //Setting title of request
            request.setTitle(fileName);

            //Setting description of request
            request.setDescription("Your file is downloading");

            //set notification when download completed
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            //Set the local destination for the downloaded file to a path within the application's external files directory
            request.setDestinationInExternalPublicDir(fileStorageDestinationUri, fileName);

            request.allowScanningByMediaScanner();

            //Enqueue download and save the referenceId
            downloadReference = downloadManager.enqueue(request);
        } catch (IllegalArgumentException e) {
            Toast.makeText(ctx,"Download link is broken or not availale for download",Toast.LENGTH_LONG).show();

            Log.e(TAG, "Line no: 455,Method: downloadFile: Download link is broken");

        }
        return downloadReference;
    }

}
