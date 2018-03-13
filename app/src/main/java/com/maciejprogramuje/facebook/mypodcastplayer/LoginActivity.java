package com.maciejprogramuje.facebook.mypodcastplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.maciejprogramuje.facebook.mypodcastplayer.api.LoginResponse;
import com.maciejprogramuje.facebook.mypodcastplayer.api.PodcastApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginEditText)
    EditText loginEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void loginClick(View view) {
        Log.w("UWAGA", "login btn clicked!");

        String username = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();



        Log.w("UWAGA", username + ": " + password);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient().newBuilder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://parseapi.back4app.com/");
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(httpClient);
        Retrofit retrofit = builder.build();

        PodcastApi podcastApi = retrofit.create(PodcastApi.class);
        Call<LoginResponse> call = podcastApi.getLogin(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    public void registerClick(View view) {
        Log.w("UWAGA", "register btn clicked!");
    }
}
