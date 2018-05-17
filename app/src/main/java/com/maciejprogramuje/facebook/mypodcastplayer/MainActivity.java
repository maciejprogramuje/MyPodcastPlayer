package com.maciejprogramuje.facebook.mypodcastplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.maciejprogramuje.facebook.mypodcastplayer.screens.discover.DiscoverFragment;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.discover.SwitchToSubscribedEvent;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.login.LoginActivity;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.subscribed.AddActionEvent;
import com.maciejprogramuje.facebook.mypodcastplayer.screens.subscribed.SubscribedFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.nav_view)
    NavigationView navView;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private UserStorage userStorage;
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userStorage = ((App) getApplication()).getUserStorage();
        if (userStorage.hasToLogin()) {
            goToLogin();
            return;
        }

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        View headerView = navView.getHeaderView(0);
        TextView drawerNameTextView = headerView.findViewById(R.id.drawerNameTextView);
        TextView drawerMailTextView = headerView.findViewById(R.id.drawerMailTextView);
        drawerNameTextView.setText(userStorage.getFullName());
        drawerMailTextView.setText(userStorage.getEmail());

        onNavigationItemSelected(navView.getMenu().findItem(R.id.nav_subscribe));

        bus = ((App) getApplication()).getBus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            userStorage.logout();
            goToLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_subscribe) {
            showFragment(new SubscribedFragment());
        } else if (id == R.id.nav_discover) {
            showFragment(new DiscoverFragment());
        } else if (id == R.id.nav_logout) {
            userStorage.logout();
            goToLogin();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Subscribe
    public void onAddAction(AddActionEvent addActionEvent) {
        goToDiscover();
    }

    @Subscribe
    public void onSwitchToSubscribed(SwitchToSubscribedEvent switchToSubscribedEvent) {
        goToSubscribed();
    }

    private void goToSubscribed() {
        goToItem(R.id.nav_subscribe);
    }

    public void goToDiscover() {
        goToItem(R.id.nav_discover);
    }

    private void goToItem(int id) {
        MenuItem item = navView.getMenu().findItem(id);
        item.setChecked(true);
        onNavigationItemSelected(item);
    }
}
