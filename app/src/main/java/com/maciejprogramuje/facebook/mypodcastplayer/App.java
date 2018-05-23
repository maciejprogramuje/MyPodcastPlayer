package com.maciejprogramuje.facebook.mypodcastplayer;

import android.app.Application;
import android.preference.PreferenceManager;

import com.maciejprogramuje.facebook.mypodcastplayer.api.ErrorConverter;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.discover.DiscoverManager;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.login.LoginManager;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.register.RegisterManager;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.subscribed.SubscribedManager;
import com.squareup.otto.Bus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by m.szymczyk on 2018-03-14.
 */

public class App extends Application {
    private LoginManager loginManager;
    private RegisterManager registerManager;
    private UserStorage userStorage;
    private Retrofit retrofit;
    private PodcastApi podcastApi;
    private DiscoverManager discoverManager;
    private Bus bus;
    private ErrorConverter errorConverter;
    private SubscribedManager subscribedManager;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest = request.newBuilder()
                                .addHeader("X-Parse-Application-Id", "U2jFQxxfQtj0kLOvtt4u1iQKPg318MhkflXY39oG")
                                .addHeader("X-Parse-REST-API-Key", "undefined")
                                .addHeader("X-Parse-Revocable-Session", "1")
                                .addHeader("Content-Type", "application/json")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://parseapi.back4app.com/");
        builder.client(httpClient);
        builder.addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        podcastApi = retrofit.create(PodcastApi.class);

        bus = new Bus();

        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        errorConverter = new ErrorConverter(retrofit);
        loginManager = new LoginManager(userStorage, podcastApi, errorConverter);
        registerManager = new RegisterManager(userStorage, podcastApi, retrofit);
        discoverManager = new DiscoverManager(podcastApi, bus, userStorage, errorConverter);
        subscribedManager = new SubscribedManager(podcastApi, userStorage);
    }

    public SubscribedManager getSubscribedManager() {
        return subscribedManager;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public PodcastApi getPodcastApi() {
        return podcastApi;
    }

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public DiscoverManager getDiscoverManager() {
        return discoverManager;
    }

    public Bus getBus() {
        return bus;
    }
}
