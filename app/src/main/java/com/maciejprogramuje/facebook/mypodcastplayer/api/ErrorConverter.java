package com.maciejprogramuje.facebook.mypodcastplayer.api;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ErrorConverter {
    private Retrofit retrofit;

    public ErrorConverter(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public ErrorResponse convert(ResponseBody responseBody) {
        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
        try {
            return converter.convert(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
