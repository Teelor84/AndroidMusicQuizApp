package com.example.androidfinalapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = Genre.class,version=2,exportSchema = false)
public abstract class GenreDB extends RoomDatabase{

    private static GenreDB INSTANCE;
    public abstract GenreDao genreDao();

    static GenreDB getDatabase(final Context context){
        if(INSTANCE == null){
            //establish db connection
            synchronized (GenreDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GenreDB.class, "genre_points.db")
                            .fallbackToDestructiveMigration()
                            //.addCallback()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
