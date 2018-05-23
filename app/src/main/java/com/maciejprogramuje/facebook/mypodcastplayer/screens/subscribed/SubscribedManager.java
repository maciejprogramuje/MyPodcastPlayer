package com.maciejprogramuje.facebook.mypodcastplayer.screens.subscribed;

import com.maciejprogramuje.facebook.mypodcastplayer.UserStorage;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribedManager {
    private PodcastApi podcastApi;
    private UserStorage userStorage;
    private Call<PodcastResponse> call;
    private SubscribedFragment subscribedFragment;

    public SubscribedManager(PodcastApi podcastApi, UserStorage userStorage) {
        this.podcastApi = podcastApi;
        this.userStorage = userStorage;
    }

    public void loadPodcasts() {
        String where = "{\"podcastId\":{\"$select\":{\"query\":{\"className\":\"Subscription\",\"where\":{\"userId\":\"%s\"}},\"key\":\"podcastId\"}}}";
        call = podcastApi.getSubscribedPodcasts(String.format(where, userStorage.getUserId()), userStorage.getToken());
        call.enqueue(new Callback<PodcastResponse>() {
            @Override
            public void onResponse(Call<PodcastResponse> call, Response<PodcastResponse> response) {
                if(response.isSuccessful()) {
                    if(subscribedFragment != null) {
                        subscribedFragment.showPodcasts(response.body().results);
                    }
                }
            }

            @Override
            public void onFailure(Call<PodcastResponse> call, Throwable t) {

            }
        });
    }
}
