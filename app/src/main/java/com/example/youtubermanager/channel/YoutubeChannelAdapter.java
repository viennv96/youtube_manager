package com.example.youtubermanager.channel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubermanager.R;
import com.example.youtubermanager.Validate;
import com.example.youtubermanager.video.VideoActivity;
import com.example.youtubermanager.entity.YoutubeChannel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YoutubeChannelAdapter extends RecyclerView.Adapter<YoutubeChannelAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<YoutubeChannel> list = new ArrayList<>();
    private int rowLayout;
    private OnItemClickListener mlistener;

    public YoutubeChannelAdapter(Context context, ArrayList<YoutubeChannel> list, int rowLayout){
        this.mContext = context;
        this.list = list;
        this.rowLayout = rowLayout;
    }

    public void openItem(int position){
        Intent intent = new Intent(mContext, VideoActivity.class);
        intent.putExtra("urlChannel", list.get(position).getUrlChannel());
        mContext.startActivity(intent);
    }

    public void addChannelToList(YoutubeChannel channel){
        list.add(channel);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final YoutubeChannelAdapter.ViewHolder viewHolder, int position) {
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
        ImageView removeImage;
        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_id);
            name = itemView.findViewById(R.id.name_id);
            playlist = itemView.findViewById(R.id.playlist_id);
            view = itemView.findViewById(R.id.view_id);
            sub = itemView.findViewById(R.id.subcribe_id);
            videos = itemView.findViewById(R.id.videos_id);
            publicDate = itemView.findViewById(R.id.date_create_id);
            removeImage = itemView.findViewById(R.id.remove_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickOpenItem(position);
                        }
                    }
                }
            });

            removeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickRemoveItem(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {

        void onClickOpenItem(int position);

        void onClickRemoveItem(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mlistener = listener;
    }
}
