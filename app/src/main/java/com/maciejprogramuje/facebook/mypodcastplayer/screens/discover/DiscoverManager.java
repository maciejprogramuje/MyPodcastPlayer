package com.maciejprogramuje.facebook.mypodcastplayer.screens.discover;

import android.support.annotation.NonNull;
import android.util.Log;

import com.maciejprogramuje.facebook.mypodcastplayer.api.Podcast;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverManager {
    private final PodcastApi podcastApi;
    private Call<PodcastResponse> call;

    public DiscoverManager(PodcastApi podcastApi) {
        this.podcastApi = podcastApi;
    }

    public void loadPodcasts() {
        call = podcastApi.getPodcasts();
        call.enqueue(new Callback<PodcastResponse>() {
            @Override
            public void onResponse(@NonNull Call<PodcastResponse> call, @NonNull Response<PodcastResponse> response) {
                if (response.isSuccessful()) {
                    for (Podcast podcast : response.body().results) {
                        Log.w("UWAGA", "podcast: " + podcast.getTitle() + ", " + podcast.getFullUrl());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PodcastResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
