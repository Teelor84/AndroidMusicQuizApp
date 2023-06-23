package com.example.androidfinalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class SpoilerPage extends AppCompatActivity {

    private RecyclerView songrecyclerView;
    private List<String> songVideosList;
    private Button returntoresultsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spoiler_page);

        songVideosList = Arrays.asList(getResources().getStringArray(R.array.song_data));

        songrecyclerView = findViewById(R.id.spoilerrecyclerview);
        VideoAdapter songAdapter = new VideoAdapter(this, songVideosList);
        songrecyclerView.setAdapter(songAdapter);
        songrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        returntoresultsbutton = (Button) findViewById(R.id.returntoresultsbutton);
        returntoresultsbutton.setOnClickListener(returnListener);


    }


    public View.OnClickListener returnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent returnIntent = new Intent(SpoilerPage.this, ResultsPage.class);
            startActivity(returnIntent);
        }
    };



}