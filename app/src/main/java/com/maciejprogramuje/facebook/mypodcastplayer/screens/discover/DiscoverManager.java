package com.maciejprogramuje.facebook.mypodcastplayer.screens.discover;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.maciejprogramuje.facebook.mypodcastplayer.UserStorage;
import com.maciejprogramuje.facebook.mypodcastplayer.api.ErrorConverter;
import com.maciejprogramuje.facebook.mypodcastplayer.api.ErrorResponse;
import com.maciejprogramuje.facebook.mypodcastplayer.api.Podcast;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverManager {
    private final PodcastApi podcastApi;
    private Bus bus;
    private UserStorage userStorage;
    private ErrorConverter errorConverter;
    private Call<PodcastResponse> call;
    private DiscoverFragment discoverFragment;
    private Call<Subscription> subscriptionCall;

    public DiscoverManager(PodcastApi podcastApi, Bus bus, UserStorage userStorage, ErrorConverter errorConverter) {
        this.podcastApi = podcastApi;
        this.bus = bus;
        this.userStorage = userStorage;
        this.errorConverter = errorConverter;
        bus.register(this);
    }

    public void onStart(DiscoverFragment discoverFragment) {
        this.discoverFragment = discoverFragment;
    }

    public void onStop() {
        this.discoverFragment = null;
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
                    if(discoverFragment != null) {
                        discoverFragment.showPodcasts(response.body().results);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PodcastResponse> call, @NonNull Throwable t) {

            }
        });
    }

    @Subscribe
    public void onAddPodcastEvent(AddPodcastEvent event) {
        Log.w("UWAGA", "add: " + event.podcast.getTitle());
        saveSubscription(event.podcast);
    }

    private void saveSubscription(Podcast podcast) {
        String userId = userStorage.getUserId();

        Subscription subscription = new Subscription();
        subscription.podcastId = podcast.getPodcastId();
        subscription.userId = userId;

        subscription.acl = new JsonObject();

        JsonObject aclJson = new JsonObject();
        aclJson.add("read", new JsonPrimitive(true));
        aclJson.add("write", new JsonPrimitive(true));
        subscription.acl.add(userId, aclJson);

        subscriptionCall = podcastApi.postSubscription(subscription, userStorage.getToken());
        subscriptionCall.enqueue(new Callback<Subscription>() {
            @Override
            public void onResponse(Call<Subscription> call, Response<Subscription> response) {
                if(response.isSuccessful()) {
                    if(discoverFragment != null) {
                        discoverFragment.saveSuccessful();
                    }
                } else {
                    ErrorResponse errorResponse = errorConverter.convert(response.errorBody());
                    if(discoverFragment != null && errorResponse != null) {
                        discoverFragment.showError(errorResponse.error);
                    }
                }
            }

            @Override
            public void onFailure(Call<Subscription> call, Throwable t) {
                if(discoverFragment != null) {
                    discoverFragment.showError(t.getLocalizedMessage());
                }
            }
        });

    }
}
