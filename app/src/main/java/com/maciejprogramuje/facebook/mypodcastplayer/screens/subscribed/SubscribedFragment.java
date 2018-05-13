package com.maciejprogramuje.facebook.mypodcastplayer.screens.subscribed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.maciejprogramuje.facebook.mypodcastplayer.App;
import com.maciejprogramuje.facebook.mypodcastplayer.R;
import com.squareup.otto.Bus;

public class SubscribedFragment extends Fragment {
    public SubscribedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscribed, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.subscribed, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_add) {
            Bus bus = ((App) getActivity().getApplication()).getBus();
            bus.post(new AddActionEvent());
            return true;
        } else if (itemId == R.id.action_sort) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
