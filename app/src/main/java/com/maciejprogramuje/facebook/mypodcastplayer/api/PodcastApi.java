package com.maciejprogramuje.facebook.mypodcastplayer.api;

import com.maciejprogramuje.facebook.mypodcastplayer.screens.discover.Subscription;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 5742ZGPC on 2018-03-13.
 */

public interface PodcastApi {
    @GET("login")
    Call<UserResponse> getLogin(@Query("username") String username, @Query("password") String password);

    @POST("users")
    Call<UserResponse> postRegister(@Body RegisterRequest request);

    @GET("classes/Podcast")
    Call<PodcastResponse> getPodcasts();

    @POST("classes/Subscription")
    Call<Subscription> postSubscription(@Body Subscription subscription, @Header("X-Parse-Session-Token") String token);

    @GET("classes/Podcast")
    Call<PodcastResponse> getSubscribedPodcasts(@Query("where") String where, @Header("X-Parse_Session_token") String token);
}
