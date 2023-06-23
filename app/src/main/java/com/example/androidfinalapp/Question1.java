package com.example.androidfinalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.androidfinalapp.Database.GenreViewModel;

import java.sql.Time;
import java.util.Calendar;

public class Question1 extends AppCompatActivity {

    private GenreViewModel genreViewModel = new GenreViewModel(getApplication());
    private Button submitQ1button;
    private Button veriftytimebutton;
    private TimePicker q1timePicker;
    private Calendar calendar;
    private TextView time;
    int hour;
    int min;
    int formattinghour;
    int formattingminute;
    private String format = "";



    int spoilerverifyer;

    String selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        createNotificationChannel();

        Bundle extras = getIntent().getExtras();
        spoilerverifyer = extras.getInt("SpoilerCheck");

        q1timePicker = (TimePicker) findViewById(R.id.q1timePicker);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);


        submitQ1button = (Button) findViewById(R.id.submitq1button);
        submitQ1button.setOnClickListener(submitQ1Listener);
        submitQ1button.setEnabled(false);

        veriftytimebutton = (Button) findViewById(R.id.verifytimebutton);
        veriftytimebutton.setOnClickListener(verifyTimeListener);


    }

    public void setTime(View view) {
        int hour = q1timePicker.getHour();
        int min = q1timePicker.getMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        int formattinghour = hour;
        if (formattinghour == 0) {
            formattinghour += 12;
            format = "AM";
        } else if (formattinghour == 12) {
            format = "PM";
        } else if (formattinghour > 12){
            formattinghour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }


        time.setText(new StringBuilder().append(formattinghour).append(" : ").append(min).append(" ").append(format));

    }


    public View.OnClickListener verifyTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            q1value();
            Log.d("test time", "the hour is: " + q1timePicker.getHour());
            Log.d("test message", "the value picked from time is: " + selectedAnswer);
            if(selectedAnswer != null){
                submitQ1button.setEnabled(true);
            }

        }
    };

    public void q1value() {
        if (q1timePicker.getHour() < 6 && q1timePicker.getHour() > 0) {
            selectedAnswer = "Rap";
            if(spoilerverifyer == 1){
                sendNotification();
            }
        } else if (q1timePicker.getHour() > 6 && q1timePicker.getHour() < 12) {
            selectedAnswer = "Country";
            if(spoilerverifyer == 1){
                sendNotification();
            }
        } else if (q1timePicker.getHour() > 12 && q1timePicker.getHour() < 18) {
            selectedAnswer = "Rock";
            if(spoilerverifyer == 1){
                sendNotification();
            }
        } else if (q1timePicker.getHour() > 18 && q1timePicker.getHour() < 24) {
            selectedAnswer = "Jazz";
            if(spoilerverifyer == 1){
                sendNotification();
            }
        }
    }


    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    public void createNotificationChannel() {
        NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    getString(R.string.notification_channel_nameq1),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);

            notifyManager.createNotificationChannel(notificationChannel);

        }

    }

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(this, Question1.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent,
                PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text) + " " + selectedAnswer)
                .setSmallIcon(R.drawable.musicnote)
                .setAutoCancel(true)
                .setContentIntent(notificationPendingIntent);
        return notifyBuilder;
    }


    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());


    }

    public View.OnClickListener submitQ1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("check answer", "the selected answer at this point is: " + selectedAnswer);
            if(selectedAnswer == "Rap"){
                //increment value in table for rap
                genreViewModel.incrementRap();
            } else if (selectedAnswer == "Country") {
                //increment value in table for country
                genreViewModel.incrementCountry();
            } else if (selectedAnswer == "Rock") {
                //increment value in table for rock
                genreViewModel.incrementRock();
            } else if (selectedAnswer == "Jazz") {
                //increment value in table for jazz
                genreViewModel.incrementJazz();
            }
            Intent spoilerintent = new Intent(Question1.this, Question2.class);
            spoilerintent.putExtra("SpoilerCheck", spoilerverifyer);
            startActivity(spoilerintent);
        }
    };








}