package com.maciejprogramuje.facebook.mypodcastplayer.screens.discover;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maciejprogramuje.facebook.mypodcastplayer.R;
import com.maciejprogramuje.facebook.mypodcastplayer.api.Podcast;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

class DiscoverAdapter extends RecyclerView.Adapter<DiscoverViewHolder> {
    private List<Podcast> podcasts = new ArrayList<>();
    private Bus bus;

    public DiscoverAdapter(Bus bus) {
        this.bus = bus;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_discover, parent, false);

        return new DiscoverViewHolder(view, bus);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        holder.setPodcast(podcasts.get(position));
    }

    @Override
    public int getItemCount() {
        return podcasts.size();
    }

    public void setPodcasts(List<Podcast> results) {
        podcasts.clear();
        podcasts.addAll(results);
        notifyDataSetChanged();
    }
}

class DiscoverViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.podcastCoverImageView)
    ImageView podcastCoverImageView;
    @InjectView(R.id.podcastNameTextView)
    TextView podcastNameTextView;
    @InjectView(R.id.podcastEpisodesCounterTextView)
    TextView podcastEpisodesCounterTextView;
    @InjectView(R.id.podcastAddImageButton)
    ImageButton podcastAddImageButton;

    private final Bus bus;
    private Podcast podcast;

    DiscoverViewHolder(View itemView, Bus bus) {
        super(itemView);
        this.bus = bus;
        ButterKnife.inject(this, itemView);
    }

    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
        podcastNameTextView.setText(podcast.getTitle());
        String numberOfEpisodes = String.valueOf(podcast.getNumberOfEpisodes()) + " " + podcastNameTextView.getResources().getString(R.string.episodes);
        podcastEpisodesCounterTextView.setText(numberOfEpisodes);

        Glide.with(podcastCoverImageView.getContext())
                .load(podcast.getThumbUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_placeholder)
                        .centerCrop())
                .into(podcastCoverImageView);
    }

    @OnClick(R.id.podcastAddImageButton)
    public void addPodcast() {
        bus.post(new AddPodcastEvent(podcast));
    }
}


