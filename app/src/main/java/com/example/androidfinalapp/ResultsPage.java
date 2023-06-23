package com.example.androidfinalapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.androidfinalapp.Database.Genre;
import com.example.androidfinalapp.Database.GenreViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ResultsPage extends AppCompatActivity {

    private GenreViewModel genreViewModel = new GenreViewModel(getApplication());
    List<String> quizResults;
    TextView resultsDisplay;
    private Button restartButton;
    private Button spoilvideosButton;


    private Button gpsButton;

    Uri locationaddress;

    private VideoView firstsongView;
    private VideoView secondsongView;
    private String videosample1;
    private String videosample2;

    private int firstVidCurrentPosition = 0;
    private int secondVidCurrentPosition = 0;
    private static final String PLAYBACK_TIME1 = "play_time1";
    private static final String PLAYBACK_TIME2 = "play_time2";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        if(savedInstanceState != null){
            firstVidCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME1);
            secondVidCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME2);

        }

        resultsDisplay = (TextView) findViewById(R.id.genreresult);

        //compare values in each genre points, set textview to genre name of textview

        List<String> strings =  genreViewModel.compareValues();

        quizResults = strings;
        Log.d("test message", "result: " + quizResults.get(0));
        Log.d("Test message2", "strings: " + strings.get(0));
        resultsDisplay.setText(quizResults.get(0));
        //if x genre, display two specific videos
        if(quizResults.get(0).equals("Rock")){
            //wonder of you (elvis)

            videosample1 = "wonderofyou";
            //until the end (breaking benjamin)
            videosample2 = "untiltheend";
            Log.d("test message", "result: I am here%%%%%%%%%"+videosample2+videosample1 );
        } else if (quizResults.get(0).equals("Rap")) {
            //black skinhead (kanye west)
            videosample1 = "blackskinhead";
            //rapper's delight (SugerHill Gang)
            videosample2 = "rappersdelight";

        } else if (quizResults.get(0).equals("Country")) {
            //silver haired daddy (gene autry)
            videosample1 = "fiveoclock";
            //5 o clock somewhere (alan jackson)
            videosample2 = "silverhaireddaddy";
        } else if (quizResults.get(0).equals("Jazz")){
            //Love (nat king cole)
            videosample1 = "love";
            //you make me feel so young (frank sinatra)
            videosample2 = "youmakemefeelsoyoung";
        }

        //set media controller for first video
        firstsongView = (VideoView) findViewById(R.id.firstsong);
        MediaController firstsongcontroller = new MediaController(this);
        firstsongcontroller.requestFocus();
        firstsongcontroller.show(0);
        firstsongcontroller.setMediaPlayer(firstsongView);
        firstsongView.setMediaController(firstsongcontroller);

        //set media controller for second video
        secondsongView = (VideoView) findViewById(R.id.secondsong);
        MediaController secondsongcontroller = new MediaController(this);
        secondsongcontroller.requestFocus();
        secondsongcontroller.show(0);
        secondsongcontroller.setMediaPlayer(secondsongView);
        secondsongView.setMediaController(secondsongcontroller);

        System.out.println("$$$$$$$$$$"+videosample2);
        initializePlayer1();
        initializePlayer2();
        restartButton = (Button) findViewById(R.id.restartbutton);
        restartButton.setOnClickListener(restartListener);

        gpsButton = (Button) findViewById(R.id.gpsbutton);
        gpsButton.setOnClickListener(gpsListener);


        spoilvideosButton = (Button) findViewById(R.id.gotospoilerbutton);
        spoilvideosButton.setOnClickListener(spoilersListener);




    }

    protected void onStart(){
        super.onStart();

    }

    protected void onStop(){
        super.onStop();
        releasePlayer1();
        releasePlayer2();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME1, firstsongView.getCurrentPosition());
        outState.putInt(PLAYBACK_TIME2, secondsongView.getCurrentPosition());
    }

    //methods to initialize and play video 1
    private void initializePlayer1(){

        //buffer and decode video 1
        Uri video1Uri = getMedia1(videosample1);
        System.out.println(video1Uri);
        firstsongView.setVideoURI(video1Uri);

        //listener for onPrepared
        firstsongView.setOnPreparedListener(

                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        if(firstVidCurrentPosition > 0){
                            firstsongView.seekTo(firstVidCurrentPosition);
                        } else {
                            firstsongView.seekTo(1);
                        }

                        firstsongView.start();
                    }
                });

        firstsongView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        firstsongView.seekTo(0);
                    }
                }
        );
    }

    private void releasePlayer1(){
        firstsongView.stopPlayback();
    }

    private Uri getMedia1(String videosample1){
        if(URLUtil.isValidUrl(videosample1)){
            return Uri.parse(videosample1);

        } else{
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + videosample1);

        }
    }

    //methods for video player 2
    private void initializePlayer2(){
        Uri video2Uri = getMedia2(videosample2);
        System.out.println(video2Uri);
        secondsongView.setVideoURI(video2Uri);

        secondsongView.setOnPreparedListener(

                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        if(secondVidCurrentPosition > 0){
                            secondsongView.seekTo(secondVidCurrentPosition);
                        } else {
                            secondsongView.seekTo(1);
                        }

                        secondsongView.start();
                    }
                }
        );

        secondsongView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        secondsongView.seekTo(0);
                    }
                }
        );
    }

    private void releasePlayer2(){
        secondsongView.stopPlayback();
    }

    private Uri getMedia2(String videosample2){
        if(URLUtil.isValidUrl(videosample2)){
            return Uri.parse(videosample2);

        } else{
            System.out.println("%%%%%%%%%%%%android.resource://" + getPackageName() + "/raw/" + videosample2);
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + videosample2);

        }
    }

    //restart button that shows the dialog to restart the app or not
    private View.OnClickListener restartListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showAlert();
        }
    };
    //dialog for restarting app
    public void showAlert(){
        AlertDialog.Builder restartAlertBuilder = new AlertDialog.Builder(ResultsPage.this);
        restartAlertBuilder.setTitle("Restart?");
        restartAlertBuilder.setMessage("Would you like to reset the quiz?");

        restartAlertBuilder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent restartApp = new Intent(ResultsPage.this, MainActivity.class);
                int restartPendingIntentID = 1;
                PendingIntent restartPendingIntent = PendingIntent.getActivity(ResultsPage.this, restartPendingIntentID, restartApp, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager restartManager = (AlarmManager) ResultsPage.this.getSystemService(Context.ALARM_SERVICE);
                restartManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, restartPendingIntent);
                System.exit(0);
            }
        });
        restartAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        restartAlertBuilder.show();
    }

    //history gps button
    private View.OnClickListener gpsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(quizResults.get(0).equals("Rock")){
                locationaddress = Uri.parse("geo: 41.50871815574691, -81.69538995767223?q= Rock and Roll Hall of Fame Cleveland");
            } else if (quizResults.get(0).equals("Rap")) {
                locationaddress = Uri.parse("geo: 40.821913817640294, -73.93066533068419?q= Universal Hip Hop Museum New York City");
            } else if (quizResults.get(0).equals("Country")) {
                locationaddress = Uri.parse("geo: 36.15874829056685, -86.7762545460012?q= Country Music Hall of Fame and Museum Nashville");
            } else if (quizResults.get(0).equals("Jazz")) {
                locationaddress = Uri.parse("geo: 40.82232668939716, -73.94169979611912?q= The National Jazz Museum in Harlem");
            }

            Intent mapintent = new Intent(Intent.ACTION_VIEW, locationaddress);

            try {
                startActivity(mapintent);
            }catch (ActivityNotFoundException e){
                Log.d("testmapintent", "Can't handle google maps intent");
            }

        }
    };

    //go to spoilers page
    private View.OnClickListener spoilersListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent spoilvideosintent = new Intent(ResultsPage.this, SpoilerPage.class);
            startActivity(spoilvideosintent);
        }
    };

}