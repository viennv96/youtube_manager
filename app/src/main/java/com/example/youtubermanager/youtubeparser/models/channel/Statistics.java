package com.example.youtubermanager.youtubeparser.models.channel;

public class Statistics {
    private int viewCount;
    private int commentCount;
    private int subscriberCount;
    private boolean hiddenSubscriberCount;
    private int videoCount;

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(int subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public boolean isHiddenSubscriberCount() {
        return hiddenSubscriberCount;
    }

    public void setHiddenSubscriberCount(boolean hiddenSubscriberCount) {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    @Override
    public String toString() {
        return "statistics{" +
                "viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", subscriberCount=" + subscriberCount +
                ", hiddenSubscriberCount=" + hiddenSubscriberCount +
                ", videoCount=" + videoCount +
                '}';
    }
}
