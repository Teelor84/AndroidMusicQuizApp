package com.example.androidfinalapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidfinalapp.ResultsPage;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GenreRepository {

    public static List<String> comparisonResults;

    private GenreDao genreDao;

    GenreRepository(Application application){
        GenreDB gnrDB = GenreDB.getDatabase(application);
        genreDao = gnrDB.genreDao();
        new compareValuesAsyncTask(genreDao).execute();
    }

    //methods to initialize values in table
    public void insertGenres(Genre genre){
        new insertGenreAsyncTask(genreDao).execute(genre);
    }

    //methods to increment points
    public void updateRockPoints(){
        new incrementRockAsyncTask(genreDao).execute();
    }

    public void updateRapPoints(){
        new incrementRapAsyncTask(genreDao).execute();
    }

    public void updateCountryPoints(){
        new incrementCountryAsyncTask(genreDao).execute();
    }

    public void updateJazzPoints(){
        new incrementJazzAsyncTask(genreDao).execute();
    }

    //compare values
    List<String> compareValues(){
         new compareValuesAsyncTask(genreDao).execute();
         return comparisonResults;

    }

    //reset the table on startup so values don't carry over
    public void resetTable(){new resetTableAsyncTask(genreDao).execute();}

    //async tasks
    private static class insertGenreAsyncTask extends AsyncTask<Genre, Void, Void>{
        private GenreDao insertAsyncTaskDao;

        insertGenreAsyncTask(GenreDao dao){
            insertAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Genre... params){
            insertAsyncTaskDao.createGenres(params[0]);
            return null;
        }
    }
    private static class incrementRockAsyncTask extends AsyncTask<Void, Void, Void>{
        private GenreDao rockAsyncTaskDao;

        incrementRockAsyncTask(GenreDao dao){
            rockAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... genres){
            rockAsyncTaskDao.updateRockPoints();
            return null;
        }
    }

    private static class incrementRapAsyncTask extends AsyncTask<Void, Void, Void>{
        private GenreDao rapAsyncTaskDao;

        incrementRapAsyncTask(GenreDao dao){
            rapAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... genres){
            rapAsyncTaskDao.updateRapPoints();
            return null;
        }
    }

    private static class incrementCountryAsyncTask extends AsyncTask<Void, Void, Void>{
        private GenreDao countryAsyncTaskDao;

        incrementCountryAsyncTask(GenreDao dao){
            countryAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... genres){
            countryAsyncTaskDao.updateCountryPoints();
            return null;
        }
    }

    private static class incrementJazzAsyncTask extends AsyncTask<Void, Void, Void>{
        private GenreDao jazzAsyncTaskDao;

        incrementJazzAsyncTask(GenreDao dao){
            jazzAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... genres){
            jazzAsyncTaskDao.updateJazzPoints();
            return null;
        }
    }

    private static class compareValuesAsyncTask extends AsyncTask<Void, Void, List<String>>{
        private GenreDao compareDao;

        compareValuesAsyncTask(GenreDao dao){compareDao = dao;}

        @Override
        protected List<String> doInBackground(Void... voids){
            List<String> results = compareDao.compareValues();
            comparisonResults = results;
            return results;
        }

        /*@Override
        protected void onPostExecute(List<String> results){
            System.out.println("^^&&^&^&^"+results.size());
            comparisonResults = results;
        }*/
    }

    //async task to reset table
    private static class resetTableAsyncTask extends AsyncTask<Void, Void, Void>{
        private GenreDao resetTableAsyncTask;

        resetTableAsyncTask(GenreDao dao){resetTableAsyncTask = dao;}

        @Override
        protected Void doInBackground(Void... params){
            resetTableAsyncTask.resetTable();
            return null;
        }
    }


}
