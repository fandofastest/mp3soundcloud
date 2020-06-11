package com.music.free.musicapp;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.SongAdapter;
import ModalClass.SongModalClass;

/**
 * Created by Remmss on 28-08-2017.
 */

public class SongsFragment extends Fragment {

    RecyclerView recycle;
    private List<SongModalClass> listsongModalClasses = new ArrayList<>();
    private SongAdapter songAdapter;

    Context ctx;
    String q;
    ImageView play,pause;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        q=Splash_activity.q;
        ctx=getContext();

        recycle = view.findViewById(R.id.recycle);


        if (ctx instanceof MainActivity) {
            ((MainActivity)ctx).showloading();
        }
        songAdapter = new SongAdapter(listsongModalClasses,ctx);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycle.setLayoutManager(mLayoutManager);
        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setAdapter(songAdapter);


        prepareMovieData();

        ((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                listsongModalClasses.clear();
                recycle.removeAllViews();
                search_query(MainActivity.querysearch);
                songAdapter.notifyDataSetChanged();


            }
        });

        return view;
    }

    private void prepareMovieData() {
        search_query(q);
        songAdapter.notifyDataSetChanged();

    }



    public void search_query(String q){



        //String url ="https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&type=video&q="+q+"&key="+Constants.KEY;
        String url="https://api-v2.soundcloud.com/search/tracks?q="+q+"&client_id="+Splash_activity.key+"&limit=100";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

//                linearLayout.setVisibility(View.GONE);



                try {
                    JSONArray jsonArray=response.getJSONArray("collection");



                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String id= jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String imageurl= jsonObject.getString("artwork_url");
                        String duration = jsonObject.getString("full_duration");

//                        Toast.makeText(getActivity(),id,Toast.LENGTH_LONG).show();

                        SongModalClass songModalClass = new SongModalClass();
                        songModalClass.setSongName(title);
                        songModalClass.setDuration(duration);
                        songModalClass.setArtistName(q);
                        songModalClass.setId(id);
                        songModalClass.setImgurl(imageurl);
                        listsongModalClasses.add(songModalClass);

                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }

                songAdapter.notifyDataSetChanged();
                System.out.println("update"+listsongModalClasses);
                if (ctx instanceof MainActivity) {
                    ((MainActivity)ctx).hideoading();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);


    }


}