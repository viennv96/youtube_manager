package com.example.youtubermanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class YoutubeChannelAdapter extends BaseAdapter {

    Context c;
    LayoutInflater inflater;
    private List<YoutubeChannel> list;

    public YoutubeChannelAdapter(Context c, List<YoutubeChannel> list){
        this.c = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.adapter_channeldetail,parent,false);
        }

        final ImageView avatar = convertView.findViewById(R.id.avatar_id);
        TextView name = convertView.findViewById(R.id.name_id);
        TextView playlist = convertView.findViewById(R.id.playlist_id);
        TextView view = convertView.findViewById(R.id.view_id);
        TextView sub = convertView.findViewById(R.id.subcribe_id);
        TextView videos = convertView.findViewById(R.id.videos_id);
        TextView publicDate = convertView.findViewById(R.id.date_create_id);

        YoutubeChannel channel = list.get(position);
        name.setText(channel.getName());
        Validate validate = new Validate();
        playlist.setText(validate.validateNumber(channel.getPlaylist()) + " playlist");
        view.setText(validate.validateNumber(channel.getView()) + " views");
        sub.setText(validate.validateNumber(channel.getSubscribe())+ " sub");
        videos.setText(validate.validateNumber(channel.getVideos()) + " videos");
        publicDate.setText(channel.getPublicDate());
        try{
            Picasso.get().load(channel.getAvatar()).into(avatar);
        }catch (Exception e){
            Picasso.get().load("http://1.bp.blogspot.com/-Yf7wdTh5KKE/UYeTVhuf9RI/AAAAAAAABJU/O9j8RxrjuHU/s1600/youtuvetv.gif").into(avatar);
        }

        return convertView;
    }
}
