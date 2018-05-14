package com.maciejprogramuje.facebook.mypodcastplayer.api;

public class Podcast {
    String fullUrl;
    String thumbUrl;
    String title;
    String description;
    int numberOfEpisodes;
    long podcastId;

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
