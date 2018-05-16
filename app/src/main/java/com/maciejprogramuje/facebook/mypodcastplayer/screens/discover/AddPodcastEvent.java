package com.maciejprogramuje.facebook.mypodcastplayer.screens.discover;

import com.maciejprogramuje.facebook.mypodcastplayer.api.Podcast;

class AddPodcastEvent {
    public Podcast podcast;

    public AddPodcastEvent(Podcast podcast) {
        this.podcast = podcast;
    }
}
