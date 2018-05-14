package com.maciejprogramuje.facebook.mypodcastplayer.api;

public class Podcast {
    private String fullUrl;
    private String thumbUrl;
    private String title;
    private String description;
    private int numberOfEpisodes;
    private long podcastId;

    public String getFullUrl() {
        return fullUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public long getPodcastId() {
        return podcastId;
    }
}
