package com.maciejprogramuje.facebook.mypodcastplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.loginEditText)
    EditText loginEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.loginButton)
    Button loginButton;

    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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

    public void loginClick(View view) {
        Log.w("UWAGA", "login btn clicked!");


            Log.w("UWAGA", loginEditText.getText().toString());


        //String username = loginEditText.getText().toString();
        //String password = passwordEditText.getText().toString();

        //loginManager.login(username, password);
    }

    public void registerClick(View view) {
        Log.w("UWAGA", "register btn clicked!");
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
                break;
            case R.id.loginButton:
                break;
        }
    }
}
