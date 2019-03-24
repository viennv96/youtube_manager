package com.example.youtubermanager.youtubeparser;

import android.os.AsyncTask;
import android.util.Log;

import com.example.youtubermanager.entity.YoutubeChannel;
import com.example.youtubermanager.youtubeparser.models.channel.Item;
import com.example.youtubermanager.youtubeparser.models.channel.Main;

import com.example.youtubermanager.youtubeparser.models.channel.PageInfo;
import com.example.youtubermanager.youtubeparser.models.channel.Snippet;
import com.example.youtubermanager.youtubeparser.models.channel.Statistics;
import com.example.youtubermanager.youtubeparser.models.videos.Thumbnails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChannelParser extends AsyncTask<String, Void, String> {
    private OnTaskCompleted onComplete;
    private final String CHANNEL_LIVE = "1";
    private final String CHANNEL_DIE = "0";
    private final String API_KEY = "AIzaSyB-P-SqdMZazYy_0cBKtWuuaknhp5hiAN8";

    public String generateRequest(String channelUrl) {
        String key = API_KEY;
        String urlString = "https://www.googleapis.com/youtube/v3/channels/?part=snippet,contentDetails,statistics";
        try{
            String id = channelUrl.trim().split("/")[4];
            if (channelUrl.contains("channel")) {
                urlString = urlString + "&id=" + id + "&key=" + key;
            } else if (channelUrl.contains("user")) {
                urlString = urlString + "&forUsername=" + id + "&key=" + key;
            } else {
                urlString = "";
            }
        }
        catch(Exception e){
            urlString = "";
        }
        return urlString;
    }

    public void onFinish(OnTaskCompleted onComplete) {
        this.onComplete = onComplete;
    }

    @Override
    protected String doInBackground(String... ulr) {

        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ulr[0])
                .build();

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            onComplete.onError();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                Gson gson = new GsonBuilder().create();
                Main data = gson.fromJson(result, Main.class);

                PageInfo pageInfo = data.getPageInfo();

                YoutubeChannel channel = new YoutubeChannel();
                int totalResults = pageInfo.getTotalResults();
                if(totalResults==1){
                    Item item = data.getItems().get(0);
                    Snippet snippet = item.getSnippet();
                    Thumbnails thumbnails = snippet.getThumbnails();
                    Statistics statistics = item.getStatistics();
                    channel.setUrlChannel("https://www.youtube.com/channel/" + item.getId());
                    channel.setName(snippet.getTitle());
                    channel.setAvatar(thumbnails.getMedium().getUrl());
                    channel.setView(statistics.getViewCount());
                    channel.setSubscribe(statistics.getSubscriberCount());
                    channel.setVideos(statistics.getVideoCount());
                    Locale.setDefault(Locale.getDefault());
                    TimeZone tz = TimeZone.getDefault();
                    Calendar cal = Calendar.getInstance(tz);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    sdf.setCalendar(cal);
                    cal.setTime(sdf.parse(snippet.getPublishedAt()));
                    Date date = cal.getTime();

                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    String pubDateString = sdf2.format(date);
                    channel.setPublicDate(pubDateString);
                    channel.setNotification("0");
                    channel.setLive(CHANNEL_LIVE);
                }else{
                    channel.setLive(CHANNEL_DIE);
                }

                Log.i("YoutubeParser", "Youtube data parsed correctly!");
                onComplete.onTaskCompleted(channel);

            } catch (Exception e) {

                e.printStackTrace();
                onComplete.onError();
            }
        } else
            onComplete.onError();
    }


    public interface OnTaskCompleted {
        void onTaskCompleted(YoutubeChannel channel);
        void onError();
    }
}
