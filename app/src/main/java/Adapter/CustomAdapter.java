package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.music.play.musicapp.R;

import java.util.List;

import ModalClass.MusicModalClass;

/**
 * Created by Remmss on 28-08-2017.
 */

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<MusicModalClass> musicModalClasses;
    private Context context;

    public CustomAdapter(Context context, List<MusicModalClass> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        musicModalClasses = customizedListView;
    }
    @Override
    public int getCount() {
        return musicModalClasses.size();
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
            convertView = layoutinflater.inflate(R.layout.list_music, parent, false);
            listViewHolder.screenShot = (ImageView)convertView.findViewById(R.id.screen_shot);
            listViewHolder.musicName = (TextView)convertView.findViewById(R.id.music_name);
            listViewHolder.musicYear = (TextView)convertView.findViewById(R.id.music_year);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.screenShot.setImageResource(musicModalClasses.get(position).getImg());
        listViewHolder.musicName.setText(musicModalClasses.get(position).getMusicName());
        listViewHolder.musicYear.setText(musicModalClasses.get(position).getMusicYear());

        return convertView;
    }

    static class ViewHolder{
        ImageView screenShot;
        TextView musicName;
        TextView musicYear;

    }
}
