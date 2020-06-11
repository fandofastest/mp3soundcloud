package com.music.play.musicapp;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.nostra13.universalimageloader.core.ImageLoader;

import eightbitlab.com.blurview.BlurView;

/**
 * Created by xmuSistone on 2016/9/18.
 */
public class CommonFragment extends Fragment {
    private ImageView imageView,imageView1;
    private View address1, address2, address3, address4, address5;
    private RatingBar ratingBar;
    private View head1, head2, head3, head4;
    private String imageUrl;
    private BlurView blurView;
    private int position;

    public interface onSomeEventListener {
        public void someEvent(int s);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    final String LOG_TAG = "myLogs";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_music_poster, null);
         imageView = (ImageView) rootView.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        someEventListener.someEvent(position);
        return rootView;
    }
    public void bindData(String imageUrl, int position) {
        this.imageUrl = imageUrl;
        this.position = position;
    }
}
