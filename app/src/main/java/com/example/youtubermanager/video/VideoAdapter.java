/*
 *   Copyright 2016 Marco Gomiero
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.example.youtubermanager.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtubermanager.R;
import com.example.youtubermanager.Validate;
import com.example.youtubermanager.youtubeparser.VideoStats;
import com.example.youtubermanager.youtubeparser.models.stats.Statistics;
import com.example.youtubermanager.youtubeparser.models.videos.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by marco on 6/15/16.
 *
 * Adapter for the recycler view
 *
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<Video> videos;
    private int rowLayout;
    private Context mContext;
    //TODO: delete
    private final String API_KEY ="AIzaSyB-P-SqdMZazYy_0cBKtWuuaknhp5hiAN8";
    Validate validate = new Validate();


    public VideoAdapter(ArrayList<Video> list, int rowLayout, Context context) {
        this.videos = list;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public void clearData(){
        if (videos != null)
            videos.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position)  {

        final Video currentVideo = videos.get(position);

        String pubDateString = currentVideo.getDate();
        final String videoTitle = currentVideo.getTitle();

        //retrieve video link
        final String videoId = currentVideo.getId();
        final String link = "https://www.youtube.com/watch?v=" + videoId;

        viewHolder.title.setText(videoTitle);
        viewHolder.pubDate.setText(pubDateString);

        try{
            Picasso.get().load(currentVideo.getThumbnails()).into(viewHolder.image);
        }catch (Exception e){
            Picasso.get().load("http://1.bp.blogspot.com/-Yf7wdTh5KKE/UYeTVhuf9RI/AAAAAAAABJU/O9j8RxrjuHU/s1600/youtuvetv.gif").into(viewHolder.image);
        }

        VideoStats videoStats = new VideoStats();
        String url = videoStats.generateStatsRequest(videoId, API_KEY);
        videoStats.execute(url);
        videoStats.onFinish(new VideoStats.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Statistics stats) {
                viewHolder.view.setText(validate.validateNumber(stats.getViewCount()) + " views");
                viewHolder.like.setText(validate.validateNumber(stats.getLikeCount()) + " like");
                viewHolder.dislike.setText(validate.validateNumber(stats.getDislikeCount()) + " dislike");
                viewHolder.comment.setText(validate.validateNumber(stats.getCommentCount()) + " comment");
            }

            @Override
            public void onError() {
                Toast.makeText(mContext, "Unable to get statistic for this video. Please try again", Toast.LENGTH_SHORT).show();
            }
        });

        //open the video on Youtube
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                mContext.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public ArrayList<Video> getList() {
        return videos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView pubDate;
        public TextView view;
        public TextView like;
        public TextView dislike;
        public TextView comment;
        ImageView image;

        public ViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.name_id);
            pubDate = itemView.findViewById(R.id.date_create_id);
            image = itemView.findViewById(R.id.avatar_id);
            view = itemView.findViewById(R.id.view_id);
            like = itemView.findViewById(R.id.like_id);
            dislike = itemView.findViewById(R.id.dislike_id);
            comment = itemView.findViewById(R.id.comment_id);
        }
    }
}