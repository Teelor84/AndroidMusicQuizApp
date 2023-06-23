package com.example.androidfinalapp.Database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "genre_points")
public class Genre {

    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int id;


    @ColumnInfo(name = "Genres")
    private String genre;

    @ColumnInfo(name = "Points")
    private int points;

    public Genre(){

    }


    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
