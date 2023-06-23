package com.example.androidfinalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<String> songList;
    private final LayoutInflater songlistInflater;

    public VideoAdapter(Context context, List<String> songList){
        this.songList = songList;
        songlistInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View songItemView = songlistInflater.inflate(R.layout.songvideoview,parent,false);
        return new VideoViewHolder(songItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        String songData = songList.get(position);
        holder.songTextView.setText(songData);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        private TextView songTextView;

        public VideoViewHolder(@NonNull View songItemView){
            super(songItemView);
            songTextView = (TextView) songItemView.findViewById(R.id.songitem_View);
        }
    }
}
