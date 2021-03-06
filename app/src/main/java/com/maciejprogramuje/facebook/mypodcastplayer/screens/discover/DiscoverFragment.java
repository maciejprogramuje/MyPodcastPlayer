package com.maciejprogramuje.facebook.mypodcastplayer.screens.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.maciejprogramuje.facebook.mypodcastplayer.App;
import com.maciejprogramuje.facebook.mypodcastplayer.R;
import com.maciejprogramuje.facebook.mypodcastplayer.api.Podcast;
import com.squareup.otto.Bus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DiscoverFragment extends Fragment {
    @InjectView(R.id.discoverRecyclerView)
    RecyclerView discoverRecyclerView;

    private DiscoverManager discoverManager;
    private Bus bus;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = (App) getActivity().getApplication();
        discoverManager = app.getDiscoverManager();
        bus = app.getBus();
    }

    @Override
    public void onStart() {
        super.onStart();
        discoverManager.onStart(this);
        discoverManager.loadPodcasts();
    }

    @Override
    public void onStop() {
        super.onStop();
        discoverManager.onStop();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void showPodcasts(List<Podcast> results) {
        DiscoverAdapter adapter = new DiscoverAdapter(bus);
        adapter.setPodcasts(results);
        discoverRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        discoverRecyclerView.setAdapter(adapter);
    }

    public void saveSuccessful() {
        bus.post(new SwitchToSubscribedEvent());
    }

    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
