package com.maciejprogramuje.facebook.mypodcastplayer.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 5742ZGPC on 2018-03-13.
 */

public interface PodcastApi {
    @Headers({
            "X-Parse-Application-Id: U2jFQxxfQtj0kLOvtt4u1iQKPg318MhkflXY39oG",
            "X-Parse-REST-API-Key: undefined",
            "X-Parse-Revocable-Session: 1"
    })
    @GET("login")
    Call<UserResponse> getLogin(@Query("username") String username, @Query("password") String password);


    @Headers({
            "X-Parse-Application-Id: U2jFQxxfQtj0kLOvtt4u1iQKPg318MhkflXY39oG",
            "X-Parse-REST-API-Key: undefined",
            "X-Parse-Revocable-Session: 1",
            "Content-Type: application/json"
    })
    @POST("users")
    Call<UserResponse> postRegister(@Body RegisterRequest request);
}
