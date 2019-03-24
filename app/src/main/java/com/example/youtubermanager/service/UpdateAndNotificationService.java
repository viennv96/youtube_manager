package com.example.youtubermanager.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.example.youtubermanager.entity.YoutubeChannel;
import com.example.youtubermanager.sqlitedb.DBAccess;
import com.example.youtubermanager.youtubeparser.ChannelParser;

import java.util.ArrayList;

public class UpdateAndNotificationService extends Service {

    private final int TIME_REQUEST = 1;
    Handler mHandler = new Handler();

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                DBAccess db = DBAccess.getInstance(getApplicationContext());
                ArrayList<YoutubeChannel> list = db.getChannel("ALL");
                for(final YoutubeChannel item:list){
                    ChannelParser parser = new ChannelParser();
                    final String url = parser.generateRequest(item.getUrlChannel());
                    parser.execute(url);
                    parser.onFinish(new ChannelParser.OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(YoutubeChannel channel) {
                            if(!item.getLive().equals(channel.getLive())){
                                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification notify=new Notification.Builder
                                        (getApplicationContext()).setContentTitle("Channel Status Change").setContentText("Your Channel status of " + "[channel.getName()]\"" +
                                        " has change").setSmallIcon(android.support.drawerlayout.R.drawable.notify_panel_notification_icon_bg).build();
                                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                                notif.notify(0, notify);
                            }
                            if(item.getVideos() != channel.getVideos()){
                                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification notify=new Notification.Builder
                                        (getApplicationContext()).setContentTitle("Channel Status Video Change").setContentText("Your video of " + "[channel.getName()]\"" +
                                        " has change").setSmallIcon(android.support.drawerlayout.R.drawable.notify_panel_notification_icon_bg).build();
                                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                                notif.notify(0, notify);
                            }

                            if(true){
                                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification notify=new Notification.Builder
                                        (getApplicationContext()).setContentTitle("Test service notification").setContentText("Content of notification service test").setSmallIcon(android.support.drawerlayout.R.drawable.notify_panel_notification_icon_bg).build();
                                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                                notif.notify(0, notify);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }

                mHandler.postDelayed(this, TIME_REQUEST*60*1000);
            }
        }, 1000);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
