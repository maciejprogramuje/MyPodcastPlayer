package com.maciejprogramuje.facebook.mypodcastplayer;

import android.app.Application;

/**
 * Created by m.szymczyk on 2018-03-14.
 */

public class App extends Application {
    private LoginManager loginManager;

    @Override
    public void onCreate() {
        super.onCreate();

        loginManager = new LoginManager();
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }
}
