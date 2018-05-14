package com.maciejprogramuje.facebook.mypodcastplayer.screens.discover;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.maciejprogramuje.facebook.mypodcastplayer.R;
import com.maciejprogramuje.facebook.mypodcastplayer.api.Podcast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

class DiscoverAdapter extends RecyclerView.Adapter<DiscoverHolder> {
    List<Podcast> podcasts = new ArrayList<>();

    @NonNull
    @Override
    public DiscoverHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_discover, parent, false);
        return new DiscoverHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverHolder holder, int position) {
        Podcast podcast = podcasts.get(position);
        holder.podcastNameTextView.setText(podcast.getTitle());
        holder.podcastEpisodesCounterTextView.setText(podcast.getNumberOfEpisodes());
    }

    @Override
    public int getItemCount() {
        return podcasts.size();
    }
}

class DiscoverHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.podcastCoverImageView)
    ImageView podcastCoverImageView;
    @InjectView(R.id.podcastNameTextView)
    TextView podcastNameTextView;
    @InjectView(R.id.podcastEpisodesCounterTextView)
    TextView podcastEpisodesCounterTextView;
    @InjectView(R.id.podcastAddImageButton)
    ImageButton podcastAddImageButton;

    public DiscoverHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
