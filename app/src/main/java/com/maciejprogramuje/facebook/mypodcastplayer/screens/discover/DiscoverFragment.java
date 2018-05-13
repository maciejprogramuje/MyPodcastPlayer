package com.maciejprogramuje.facebook.mypodcastplayer.screens.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maciejprogramuje.facebook.mypodcastplayer.App;
import com.maciejprogramuje.facebook.mypodcastplayer.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DiscoverFragment extends Fragment {
    @InjectView(R.id.discoverRecyclerView)
    RecyclerView discoverRecyclerView;

    private DiscoverManager discoverManager;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        discoverManager = ((App) getActivity().getApplication()).getDiscoverManager();
    }

    @Override
    public void onStart() {
        super.onStart();
        discoverManager.loadPodcasts();
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
}
