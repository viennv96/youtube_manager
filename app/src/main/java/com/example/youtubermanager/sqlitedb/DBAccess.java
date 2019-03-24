package com.example.youtubermanager.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.youtubermanager.entity.YoutubeChannel;

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
    public ArrayList<YoutubeChannel> getChannel(String status){
        ArrayList<YoutubeChannel> list = new ArrayList<>();
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

    public void addNewChannel(YoutubeChannel channel){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", channel.getUrlChannel());
        contentValues.put("name", channel.getName());
        contentValues.put("avatar", channel.getAvatar());
        contentValues.put("playlist", channel.getPlaylist());
        contentValues.put("view", channel.getView());
        contentValues.put("subscribe", channel.getSubscribe());
        contentValues.put("videos", channel.getVideos());
        contentValues.put("publicDate", channel.getPublicDate());
        contentValues.put("notification", channel.getNotification());
        contentValues.put("live", channel.getLive());
        database.insert("YoutubeChannel", null, contentValues);
        close();
    }

    public void deleteChannel(String channelUrl){
        open();
        String clause = "url=?";
        String args[] = new String[] {channelUrl};
        database.delete("YoutubeChannel", clause, args);
        close();
    }
}
