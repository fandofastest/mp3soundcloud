package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.music.free.musicapp.R;

import java.util.List;

import ModalClass.ArtistModalClass;

/**
 * Created by Remmss on 28-08-2017.
 */

public class ArtistAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ArtistModalClass> artistModalClasses;
    private Context context;

    public ArtistAdapter(Context context, List<ArtistModalClass> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        artistModalClasses = customizedListView;
    }
    @Override
    public int getCount() {
        return artistModalClasses.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.list_artist, parent, false);
            listViewHolder.artist_image = (ImageView)convertView.findViewById(R.id.img_artist);
            listViewHolder.artist_name = (TextView)convertView.findViewById(R.id.artist_name);
            listViewHolder.total_songs = (TextView)convertView.findViewById(R.id.total_songs);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.artist_image.setImageResource(artistModalClasses.get(position).getImg());
        listViewHolder.artist_name.setText(artistModalClasses.get(position).getArtistName());
        listViewHolder.total_songs.setText(artistModalClasses.get(position).getTotalSongs());

        return convertView;
    }

    static class ViewHolder{
        ImageView artist_image;
        TextView artist_name;
        TextView total_songs;

    }
}
