package com.maciejprogramuje.facebook.mypodcastplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.passwordEditText)
    EditText loginEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void loginClick(View view) {
        Log.w("UWAGA", "login btn clicked!");
    }

    public void registerClick(View view) {
        Log.w("UWAGA", "register btn clicked!");
    }
}
