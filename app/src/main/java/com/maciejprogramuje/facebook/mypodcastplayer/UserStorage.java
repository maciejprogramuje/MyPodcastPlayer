package com.maciejprogramuje.facebook.mypodcastplayer;

import android.content.SharedPreferences;

import com.maciejprogramuje.facebook.mypodcastplayer.api.UserResponse;

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

    public void save(UserResponse userResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_TOKEN_KEY, userResponse.sessionToken);
        editor.putString(USERNAME_KEY, userResponse.username);
        editor.putString(EMAIL_KEY, userResponse.email);
        editor.putString(USER_ID_KEY, userResponse.objectId);
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
