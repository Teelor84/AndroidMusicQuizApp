package com.example.androidfinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.example.androidfinalapp.Database.Genre;
import com.example.androidfinalapp.Database.GenreViewModel;

public class MainActivity extends AppCompatActivity {

    //private CheckBox spoilerbutton;
    CheckBox spoilerbutton;
    private Button beginbutton;

    private GenreViewModel genreViewModel;

    //indicates whether the questions will send notifications when an option is selected
    int spoilercheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        genreViewModel = new GenreViewModel(getApplication());

        genreViewModel.resetTable();
        insertRock();
        insertRap();
        insertCountry();
        insertJazz();

        spoilerbutton = (CheckBox) findViewById(R.id.spoilernotifcheckbox);
        beginbutton = (Button) findViewById(R.id.beginbutton);
        beginbutton.setOnClickListener(beginListener);

    }

    public void insertRock(){
        Genre rockgenre = new Genre();
        rockgenre.setGenre("Rock");
        rockgenre.setPoints(0);
        genreViewModel.insertGenre(rockgenre);
    }
    public void insertRap(){
        Genre rapgenre = new Genre();
        rapgenre.setGenre("Rap");
        rapgenre.setPoints(0);
        genreViewModel.insertGenre(rapgenre);
    }
    public void insertCountry(){
        Genre countrygenre = new Genre();
        countrygenre.setGenre("Country");
        countrygenre.setPoints(0);
        genreViewModel.insertGenre(countrygenre);
    }
    public void insertJazz(){
        Genre jazzgenre = new Genre();
        jazzgenre.setGenre("Jazz");
        jazzgenre.setPoints(0);
        genreViewModel.insertGenre(jazzgenre);
    }



    public void SpoilerCheck(View view){
        if(spoilerbutton.isChecked()){
            spoilercheck = 1;
            Log.d("check", "Value of spoiler check: " + spoilercheck);
        }
    }

    public View.OnClickListener beginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent spoilerintent = new Intent(MainActivity.this, Question1.class);
            spoilerintent.putExtra("SpoilerCheck", spoilercheck);
            startActivity(spoilerintent);
        }
    };

}