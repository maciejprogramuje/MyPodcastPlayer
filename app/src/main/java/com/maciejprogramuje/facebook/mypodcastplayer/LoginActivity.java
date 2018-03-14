package com.maciejprogramuje.facebook.mypodcastplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginEditText)
    EditText loginEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginManager = new LoginManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginManager.onStop();
    }

    public void loginClick(View view) {
        Log.w("UWAGA", "login btn clicked!");

        String username = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        loginManager.login(username, password);
    }

    public void registerClick(View view) {
        Log.w("UWAGA", "register btn clicked!");
    }


    public void showError(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
