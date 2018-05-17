package com.maciejprogramuje.facebook.mypodcastplayer.screens.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.maciejprogramuje.facebook.mypodcastplayer.MainActivity;
import com.maciejprogramuje.facebook.mypodcastplayer.UserStorage;
import com.maciejprogramuje.facebook.mypodcastplayer.api.ErrorConverter;
import com.maciejprogramuje.facebook.mypodcastplayer.api.ErrorResponse;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;
import com.maciejprogramuje.facebook.mypodcastplayer.api.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginManager {
    private LoginActivity loginActivity;
    private UserStorage userStorage;
    private final PodcastApi podcastApi;
    private ErrorConverter errorConverter;
    private boolean isDuringLoging;

    public LoginManager(UserStorage userStorage, PodcastApi podcastApi, ErrorConverter errorConverter) {
        this.userStorage = userStorage;
        this.podcastApi = podcastApi;
        this.errorConverter = errorConverter;
    }

    public void onAttach(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void onStop() {
        loginActivity = null;
    }

    public void login(String username, String password) {
        if(!isDuringLoging) {
            isDuringLoging = true;
            updateProgress();

            Call<UserResponse> call = podcastApi.getLogin(username, password);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                    isDuringLoging = false;
                    updateProgress();
                    if (response.isSuccessful()) {
                        Log.w("UWAGA", "Response -> " + response);
                        userStorage.save(response.body());
                        if (loginActivity != null) {
                            loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
                            loginActivity.finish();
                        }
                    } else {
                        ResponseBody responseBody = response.errorBody();
                        ErrorResponse errorResponse = errorConverter.convert(responseBody);
                        if(loginActivity != null && errorResponse != null) {
                            Log.w("UWAGA", "ErrorResponse -> " + errorResponse);
                            loginActivity.showError(errorResponse.toString());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
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
