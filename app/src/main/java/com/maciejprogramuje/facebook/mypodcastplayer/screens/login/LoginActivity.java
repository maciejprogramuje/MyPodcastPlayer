package com.maciejprogramuje.facebook.mypodcastplayer.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maciejprogramuje.facebook.mypodcastplayer.App;
import com.maciejprogramuje.facebook.mypodcastplayer.R;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.register.RegisterActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {
    @InjectView(R.id.loginEditText)
    EditText loginEditText;
    @InjectView(R.id.passwordEditText)
    EditText passwordEditText;
    @InjectView(R.id.registerButton)
    Button registerButton;
    @InjectView(R.id.loginButton)
    Button loginButton;

    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        loginManager = ((App) getApplication()).getLoginManager();
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

    public void showError(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void showProgress(boolean progress) {
        loginButton.setEnabled(!progress);
    }

    @OnClick({R.id.registerButton, R.id.loginButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.loginButton:
                String username = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                loginManager.login(username, password);
                break;
        }
    }
}
