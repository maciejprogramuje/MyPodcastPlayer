package com.maciejprogramuje.facebook.mypodcastplayer.screens.discover;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Subscription {
    public String userId;
    public long podcastId;

    @SerializedName("ACL")
    public JsonObject acl;
}
