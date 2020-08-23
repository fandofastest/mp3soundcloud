package com.music.free.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.music.free.musicapp.SearchFragment;
import com.music.free.musicapp.SongsFragment;


/**
 * Created by praja on 06-06-17.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

   static final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "SONGS", "SEARCH"};



    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                SongsFragment songsFragment = new SongsFragment();
                return songsFragment;
            case 1:

                SearchFragment searchFragment = new SearchFragment();
                return searchFragment;

            default:


        }

        return null;


    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }

}