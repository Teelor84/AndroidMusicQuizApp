package com.example.androidfinalapp.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GenreViewModel extends AndroidViewModel {
    private GenreRepository genreRepository;

    public GenreViewModel(@NonNull Application application){
        super(application);

        genreRepository = new GenreRepository(application);
    }

    public void insertGenre(Genre genre){
        genreRepository.insertGenres(genre);
    }
    public void incrementRock(){
        genreRepository.updateRockPoints();
    }
    public void incrementRap(){
        genreRepository.updateRapPoints();
    }
    public void incrementCountry(){
        genreRepository.updateCountryPoints();
    }
    public void incrementJazz(){
        genreRepository.updateJazzPoints();
    }
    public List<String> compareValues(){
        return genreRepository.compareValues();
    }
    public void resetTable(){genreRepository.resetTable();}
}
