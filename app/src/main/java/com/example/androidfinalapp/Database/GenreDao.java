package com.example.androidfinalapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface GenreDao {



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void createGenres(Genre genre);

    //selects the points column for Rock genre
    @Query("UPDATE genre_points SET Points = Points + 1 WHERE Genres = 'Rock'")
    void  updateRockPoints();

    @Query("UPDATE genre_points SET Points = Points+1 WHERE Genres = 'Rap'")
    void updateRapPoints();

    @Query("UPDATE genre_points SET Points = Points + 1 WHERE Genres = 'Country'")
    void updateCountryPoints();

    @Query("UPDATE genre_points SET Points = Points + 1 WHERE Genres = 'Jazz'")
    void updateJazzPoints();

    //want to select the genre column with the most points and then set it to a string
    @Query("SELECT Genres FROM genre_points WHERE Points=(SELECT MAX(Points) FROM genre_points)")
    List<String> compareValues();

    @Query("DELETE FROM genre_points")
    void resetTable();





}
