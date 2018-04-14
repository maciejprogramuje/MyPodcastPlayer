package com.maciejprogramuje.facebook.mypodcastplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        Log.w("UWAGA", "loginManager" + loginManager);
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
                Log.w("UWAGA", "register btn clicked!");
                break;
            case R.id.loginButton:
                Log.w("UWAGA", "login btn clicked!");

                String username = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                loginManager.login(username, password);
                break;
        }
    }
}
