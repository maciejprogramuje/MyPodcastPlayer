package com.maciejprogramuje.facebook.mypodcastplayer;

import android.app.Application;
import android.preference.PreferenceManager;

import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.discover.DiscoverManager;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.login.LoginManager;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.register.RegisterManager;
import com.squareup.otto.Bus;

import okhttp3.OkHttpClient;
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

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://parseapi.back4app.com/");
        builder.client(httpClient);
        builder.addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        podcastApi = retrofit.create(PodcastApi.class);

        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        loginManager = new LoginManager(userStorage, podcastApi, retrofit);
        registerManager = new RegisterManager(userStorage, podcastApi, retrofit);
        discoverManager = new DiscoverManager(podcastApi);

        bus = new Bus();
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
