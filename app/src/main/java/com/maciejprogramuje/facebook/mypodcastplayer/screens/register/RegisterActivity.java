package com.maciejprogramuje.facebook.mypodcastplayer.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maciejprogramuje.facebook.mypodcastplayer.App;
import com.maciejprogramuje.facebook.mypodcastplayer.MainActivity;
import com.maciejprogramuje.facebook.mypodcastplayer.R;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @InjectView(R.id.passwordEditText)
    EditText passwordEditText;
    @InjectView(R.id.emailEditText)
    EditText emailEditText;
    @InjectView(R.id.lastnameEditText)
    EditText lastnameEditText;
    @InjectView(R.id.firstnameEditText)
    EditText firstnameEditText;
    @InjectView(R.id.registerButton)
    Button registerButton;

    RegisterManager registerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        registerManager = ((App) getApplication()).getRegisterManager();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerManager.onStop();
    }

    @OnClick(R.id.registerButton)
    public void onViewClicked() {
        tryToRegister();
    }

    private void tryToRegister() {
        String firstname = firstnameEditText.getText().toString();
        String lastname = lastnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        registerManager.register(firstname, lastname, email, password);
    }

    public void registerSuccessFull() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }

    public void showProgress(boolean inProgress) {

    }
}
