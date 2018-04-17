package com.maciejprogramuje.facebook.mypodcastplayer.screens.register;

import com.maciejprogramuje.facebook.mypodcastplayer.UserStorage;
import com.maciejprogramuje.facebook.mypodcastplayer.api.ErrorResponse;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;
import com.maciejprogramuje.facebook.mypodcastplayer.api.RegisterRequest;
import com.maciejprogramuje.facebook.mypodcastplayer.api.UserResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterManager {
    private RegisterActivity registerActivity;
    private final UserStorage userStorage;
    private final PodcastApi podcastApi;
    private final Retrofit retrofit;
    private Call<UserResponse> userResponseCall;

    public RegisterManager(UserStorage userStorage, PodcastApi podcastApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.podcastApi = podcastApi;
        this.retrofit = retrofit;
    }
    
    public void onAttach(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
        updateProgress();
    }

    public void onStop() {
        this.registerActivity = null;
    }
    
    public void register(final String firstName, final String lastName, final String email, String password) {
        final RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.firstName = firstName;
        registerRequest.lastName = lastName;
        registerRequest.email = email;
        registerRequest.username = email;
        registerRequest.password = password;

        if(userResponseCall == null) {
            userResponseCall = podcastApi.postRegister(registerRequest);
            updateProgress();
            userResponseCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    userResponseCall = null;
                    updateProgress();

                    if(response.isSuccessful()) {
                        UserResponse body = response.body();
                        body.firstName = firstName;
                        body.lastName = lastName;
                        body.email = email;
                        body.username = email;
                        userStorage.save(body);
                        if(registerActivity != null) {
                            registerActivity.registerSuccessFull();
                        }
                    } else {
                        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                        try {
                            ErrorResponse errorResponse = converter.convert(response.errorBody());
                            if(registerActivity != null) {
                                registerActivity.showError(errorResponse.error);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    userResponseCall = null;
                    updateProgress();

                    if(registerActivity != null) {
                        registerActivity.showError(t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void updateProgress() {
        if(registerActivity != null) {
            registerActivity.showProgress(userResponseCall != null);
        }
    }

}
