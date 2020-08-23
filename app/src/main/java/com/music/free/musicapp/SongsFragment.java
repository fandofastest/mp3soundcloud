package com.music.play.musicapp;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.music.free.adapter.SongAdapter;
import com.music.free.modalclass.SongModalClass;

/**
 * Created by Remmss on 28-08-2017.
 */

public class SongsFragment extends Fragment {

    RecyclerView recycle;
    private List<SongModalClass> listsongModalClasses = new ArrayList<>();
    private SongAdapter songAdapter;
    LinearLayout admoblayout;
    Context ctx;
    String q;
    ImageView play,pause;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        q=Constants.q;
        ctx=getContext();

        recycle = view.findViewById(R.id.recycle);


        if (ctx instanceof MainActivity) {
            ((MainActivity)ctx).showLoading();
        }
        songAdapter = new SongAdapter(Constants.getListsongModalClasses(),ctx);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycle.setLayoutManager(mLayoutManager);
        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setAdapter(songAdapter);

        admoblayout=view.findViewById(R.id.banner_container);
        Ads ads  = new Ads();
        Display display =getActivity().getWindowManager().getDefaultDisplay();
        ads.ShowBannerAds(ctx,admoblayout,Constants.getBannerfan(),Constants.getBanner(),display);



        prepareMovieData();

        ((MainActivity)getActivity()).setFragmentRefreshListener(() -> {
            Constants.setListsongModalClassesClear();
            recycle.removeAllViews();
            searchQuery(Constants.getQuerysearch());
            songAdapter.notifyDataSetChanged();


        });

        return view;
    }

    private void prepareMovieData() {
        searchQuery(q);
        songAdapter.notifyDataSetChanged();

    }



    public void searchQuery(String q){



        String url="https://api-v2.soundcloud.com/search/tracks?q="+q+"&client_id="+Constants.getKey()+"&limit=100";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {




            try {
                JSONArray jsonArray=response.getJSONArray("collection");



                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                    String id= jsonObject.getString("id");
                    String title = jsonObject.getString("title");
                    String imageurl= jsonObject.getString("artwork_url");
                    String duration = jsonObject.getString("full_duration");


                    SongModalClass songModalClass = new SongModalClass();
                    songModalClass.setSongName(title);
                    songModalClass.setDuration(duration);
                    songModalClass.setArtistName(q);
                    songModalClass.setId(id);
                    songModalClass.setImgurl(imageurl);
                    Constants.addListsongModalClasses(songModalClass);
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }

            songAdapter.notifyDataSetChanged();
            if (ctx instanceof MainActivity) {
                ((MainActivity)ctx).hideLoading();
            }



        }, error -> Log.e("err",error.getMessage()));

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);


    }




}