package com.example.youtubermanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DBAccess instance;
    Cursor c = null;

    private DBAccess(Context context){
        this.openHelper = new DBHelper(context);
    }

    public static DBAccess getInstance(Context context){
        if(instance==null){
            instance = new DBAccess(context);
        }
        return instance;
    }

    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if(database!=null){
            this.database.close();
        }
    }

    /**
     * get list of chanel from db
     * @param status ALL
     * @return list all channel
     * @param status "LIVE"
     * @return only live channel
     * @param status "DIE"
     * @return only die channel
     */
    public List<YoutubeChannel> getChannel(String status){
        List<YoutubeChannel> list = new ArrayList<>();
        String query = "SELECT * FROM YoutubeChannel";
        switch (status){
            case "ALL":
                //do nothing
                break;
            case "LIVE":
                query += " WHERE LIVE = '1'";
                    break;
            case "DIE":
                query += " WHERE LIVE = '0'";
                break;
                default:
                    //do nothing
                    break;
        }
        open();
        c=database.rawQuery(query, null);
        while(c.moveToNext()){
            YoutubeChannel channel = new YoutubeChannel();
            channel.setUrlChannel(c.getString(0));
            channel.setName(c.getString(1));
            channel.setAvatar(c.getString(2));
            channel.setPlaylist(c.getInt(c.getColumnIndex("playlist")));
            channel.setView(c.getInt(4));
            channel.setSubscribe(c.getInt(5));
            channel.setVideos(c.getInt(6));
            channel.setPublicDate(c.getString(7));
            channel.setNotification(c.getString(8));
            channel.setLive(c.getString(9));
            list.add(channel);
        }
        close();
        return list;
    }

    /**
     * get number of chanel from db
     * @param status ALL
     * @return list all channel
     * @param status "LIVE"
     * @return only live channel
     * @param status "DIE"
     * @return only die channel
     */
    public int getNumberChannel(String status){
        String query = "SELECT COUNT (*) FROM YoutubeChannel";
        switch (status){
            case "ALL":
                //do nothing
                break;
            case "LIVE":
                query += " WHERE LIVE = '1'";
                break;
            case "DIE":
                query += " WHERE LIVE = '0'";
                break;
            default:
                //do nothing
                break;
        }
        open();
        c=database.rawQuery(query, null);
        while (c.moveToNext()){
            return c.getInt(0);
        }
        return 0;
    }

//    public List<YoutubeChannel> getLiveChannel(){
//        List<YoutubeChannel> list = new ArrayList<>();
//        String query = "SELECT * FROM YoutubeChannel WHERE live = '1'";
//        open();
//        c=database.rawQuery(query, null);
//        while(c.moveToNext()){
//            YoutubeChannel channel = new YoutubeChannel();
//            channel.setUrlChannel(c.getString(0));
//            channel.setName(c.getString(1));
//            channel.setAvatar(c.getString(2));
//            channel.setPlaylist(c.getInt(c.getColumnIndex("playlist")));
//            channel.setView(c.getInt(4));
//            channel.setSubscribe(c.getInt(5));
//            channel.setVideos(c.getInt(6));
//            channel.setPublicDate(c.getString(7));
//            channel.setNotification(c.getString(8));
//            channel.setLive(c.getString(9));
//            list.add(channel);
//        }
//        close();
//        return list;
//    }

//    public List<YoutubeChannel> getDieChannel(){
//        List<YoutubeChannel> list = new ArrayList<>();
//        String query = "SELECT * FROM YoutubeChannel WHERE live = '0'";
//        open();
//        c=database.rawQuery(query, null);
//        while(c.moveToNext()){
//            YoutubeChannel channel = new YoutubeChannel();
//            channel.setUrlChannel(c.getString(0));
//            channel.setName(c.getString(1));
//            channel.setAvatar(c.getString(2));
//            channel.setPlaylist(c.getInt(c.getColumnIndex("playlist")));
//            channel.setView(c.getInt(4));
//            channel.setSubscribe(c.getInt(5));
//            channel.setVideos(c.getInt(6));
//            channel.setPublicDate(c.getString(7));
//            channel.setNotification(c.getString(8));
//            channel.setLive(c.getString(9));
//            list.add(channel);
//        }
//        close();
//        return list;
//    }
}
