package com.maciejprogramuje.facebook.mypodcastplayer;

import android.content.Intent;
import android.support.annotation.NonNull;
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

public class LoginManager {
    private LoginActivity loginActivity;
    private UserStorage userStorage;
    boolean isDuringLoging;

    public LoginManager(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

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

        if(!isDuringLoging) {
            isDuringLoging = true;
            updateProgress();

            Call<LoginResponse> call = podcastApi.getLogin(username, password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    isDuringLoging = false;
                    updateProgress();
                    if (response.isSuccessful()) {
                        Log.w("UWAGA", "Response -> " + response);
                        userStorage.login(response.body());
                        if (loginActivity != null) {
                            loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
                            loginActivity.finish();
                        }
                    } else {
                        ResponseBody responseBody = response.errorBody();
                        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                        try {
                            assert responseBody != null;
                            ErrorResponse errorResponse = converter.convert(responseBody);
                            if(loginActivity != null) {
                                Log.w("UWAGA", "ErrorResponse -> " + errorResponse);
                                loginActivity.showError(errorResponse.toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    isDuringLoging = false;
                    updateProgress();
                    loginActivity.showError(t.getLocalizedMessage());
                }
            });
        }
    }

    private void updateProgress() {
        if(loginActivity != null) {
            loginActivity.showProgress(isDuringLoging);
        }
    }
}
