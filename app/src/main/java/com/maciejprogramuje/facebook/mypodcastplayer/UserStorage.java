package com.maciejprogramuje.facebook.mypodcastplayer;

import android.content.SharedPreferences;

import com.maciejprogramuje.facebook.mypodcastplayer.api.LoginResponse;

/**
 * Created by 5742ZGPC on 2018-03-17.
 */

public class UserStorage {
    public static final String SESSION_TOKEN_KEY = "sessionToken";
    public static final String USERNAME_KEY = "username";
    public static final String EMAIL_KEY = "email";
    public static final String USER_ID_KEY = "userId";
    private final SharedPreferences sharedPreferences;

    public UserStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void login(LoginResponse loginResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_TOKEN_KEY, loginResponse.sessionToken);
        editor.putString(USERNAME_KEY, loginResponse.username);
        editor.putString(EMAIL_KEY, loginResponse.email);
        editor.putString(USER_ID_KEY, loginResponse.objectId);
        editor.apply();
    }

    public boolean hasToLogin() {
        return sharedPreferences.getString(SESSION_TOKEN_KEY, "").isEmpty();
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }
}
