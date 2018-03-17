package com.maciejprogramuje.facebook.mypodcastplayer;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * Created by m.szymczyk on 2018-03-14.
 */

public class App extends Application {
    private LoginManager loginManager;
    private UserStorage userStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        loginManager = new LoginManager(userStorage);
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
