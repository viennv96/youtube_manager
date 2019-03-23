package com.example.youtubermanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class YoutubeChannelAdapter extends RecyclerView.Adapter<YoutubeChannelAdapter.ViewHolder> {

    Context mContext;
    private List<YoutubeChannel> list;
    private int rowLayout;

    public YoutubeChannelAdapter(Context context, List<YoutubeChannel> list, int rowLayout){
        this.mContext = context;
        this.list = list;
        this.rowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeChannelAdapter.ViewHolder viewHolder, int position) {
        final int index = position;
        YoutubeChannel channel = list.get(position);
        viewHolder.name.setText(channel.getName());
        Validate validate = new Validate();
        viewHolder.playlist.setText(validate.validateNumber(channel.getPlaylist()) + " playlist");
        viewHolder.view.setText(validate.validateNumber(channel.getView()) + " views");
        viewHolder.sub.setText(validate.validateNumber(channel.getSubscribe())+ " sub");
        viewHolder.videos.setText(validate.validateNumber(channel.getVideos()) + " videos");
        viewHolder.publicDate.setText(channel.getPublicDate());
        try{
            Picasso.get().load(channel.getAvatar()).into(viewHolder.avatar);
        }catch (Exception e){
            Picasso.get().load("http://1.bp.blogspot.com/-Yf7wdTh5KKE/UYeTVhuf9RI/AAAAAAAABJU/O9j8RxrjuHU/s1600/youtuvetv.gif").into(viewHolder.avatar);
        }

        String channelStatus = list.get(index).getLive().trim().toLowerCase();
        String channelLive = mContext.getString(R.string.channel_live).trim().toLowerCase();
        if( channelStatus.equals(channelLive)){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("urlChannel", list.get(index).getUrlChannel());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 :  list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        TextView playlist;
        TextView view;
        TextView sub;
        TextView videos;
        TextView publicDate;
        public ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_id);
            name = itemView.findViewById(R.id.name_id);
            playlist = itemView.findViewById(R.id.playlist_id);
            view = itemView.findViewById(R.id.view_id);
            sub = itemView.findViewById(R.id.subcribe_id);
            videos = itemView.findViewById(R.id.videos_id);
            publicDate = itemView.findViewById(R.id.date_create_id);
        }
    }
}
