package com.maciejprogramuje.facebook.mypodcastplayer.api;

/**
 * Created by 5742ZGPC on 2018-03-13.
 */

public class UserResponse {
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public String password;
    public String objectId;
    public String sessionToken;

    //{"objectId":"OsHr41JPWh","username":"test@test.pl","createdAt":"2018-03-13T13:33:57.265Z","updatedAt":"2018-03-14T08:13:40.566Z","email":"test@test.pl","ACL":{"*":{"read":true},"OsHr41JPWh":{"read":true,"write":true}},"sessionToken":"r:689ca77ed56513ce93985da072a14513"}

    @Override
    public String toString() {
        return "UserResponse{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", objectId='" + objectId + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
