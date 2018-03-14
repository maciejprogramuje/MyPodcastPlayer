package com.maciejprogramuje.facebook.mypodcastplayer;

import android.content.Intent;
import android.util.Log;

import com.maciejprogramuje.facebook.mypodcastplayer.api.ErrorResponse;
import com.maciejprogramuje.facebook.mypodcastplayer.api.LoginResponse;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by m.szymczyk on 2018-03-14.
 */

public class LoginManager {
    private LoginActivity loginActivity;

    public void onAttach(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void onStop() {
        loginActivity = null;
    }

    public void login(String username, String password) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://parseapi.back4app.com/");
        builder.client(httpClient);
        builder.addConverterFactory(GsonConverterFactory.create());
        final Retrofit retrofit = builder.build();

        PodcastApi podcastApi = retrofit.create(PodcastApi.class);
        Call<LoginResponse> call = podcastApi.getLogin(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.w("UWAGA", "Response -> " + response);
                    if (loginActivity != null) {
                        loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
                        loginActivity.finish();
                    }
                } else {
                    ResponseBody responseBody = response.errorBody();
                    Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                    try {
                        ErrorResponse errorResponse = converter.convert(responseBody);
                        Log.w("UWAGA", "Response -> " + errorResponse);
                        if(loginActivity != null) {
                            loginActivity.showError(errorResponse.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginActivity.showError(t.getLocalizedMessage());
            }
        });
    }
}
