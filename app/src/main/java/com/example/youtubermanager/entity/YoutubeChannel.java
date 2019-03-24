package com.example.youtubermanager.entity;

public class YoutubeChannel {
    private String urlChannel;
    private String name;
    private String avatar;
    private int playlist = 0;
    private int view = 0;
    private int subscribe = 0;
    private int videos = 0;
    private String publicDate;
    private String notification;
    private String live;

    public String getUrlChannel() {
        urlChannel = this.urlChannel == null ? "" : urlChannel;
        return urlChannel;
    }

    public void setUrlChannel(String urlChannel) {
        this.urlChannel = urlChannel;
    }

    public String getName() {
        name = this.name == null ? "" : name;
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        avatar = this.avatar == null ? "" : avatar;
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPlaylist() {
        return playlist;
    }

    public void setPlaylist(int playlist) {
        this.playlist = playlist;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public String getPublicDate() {
        publicDate = this.publicDate == null ? "" : publicDate;
        return publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
    }

    public String getNotification() {
        notification = this.notification == null ? "" : notification;
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }
}
