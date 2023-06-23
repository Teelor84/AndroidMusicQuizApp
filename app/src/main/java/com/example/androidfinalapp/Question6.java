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
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.androidfinalapp.Database.GenreViewModel;

public class Question6 extends AppCompatActivity {

    private GenreViewModel genreViewModel = new GenreViewModel(getApplication());

    private RadioButton hundredyearsbutton;
    private RadioButton seventyyearsbutton;
    private RadioButton fiftyyearsbutton;
    private RadioButton thirtyyearsbutton;
    private Button submitQ6button;
    int spoilerverifyer;

    String selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question6);

        Bundle extras = getIntent().getExtras();
        spoilerverifyer = extras.getInt("SpoilerCheck");

        hundredyearsbutton = (RadioButton) findViewById(R.id.q6100yrsbutton);
        seventyyearsbutton = (RadioButton) findViewById(R.id.q670yrsbutton);
        fiftyyearsbutton = (RadioButton) findViewById(R.id.q650yrsbutton);
        thirtyyearsbutton = (RadioButton) findViewById(R.id.q6yr30sbutton);

        hundredyearsbutton.setOnClickListener(Q6RadioButtonListener);
        seventyyearsbutton.setOnClickListener(Q6RadioButtonListener);
        fiftyyearsbutton.setOnClickListener(Q6RadioButtonListener);
        thirtyyearsbutton.setOnClickListener(Q6RadioButtonListener);

        submitQ6button = (Button) findViewById(R.id.submitq6button);
        submitQ6button.setOnClickListener(submitQ6Listener);
        submitQ6button.setEnabled(false);
    }


    private View.OnClickListener Q6RadioButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            switch(view.getId()){
                case R.id.q6100yrsbutton:
                    if(checked){
                        selectedAnswer = "Jazz";
                        submitQ6button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //sendNotification for points to jazz
                        }


                        break;
                    }
                case R.id.q670yrsbutton:
                    if(checked){
                        selectedAnswer = "Country";
                        submitQ6button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //send notifications for points to country
                        }


                        break;
                    }
                case R.id.q650yrsbutton:
                    if(checked){
                        selectedAnswer = "Rock";
                        submitQ6button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //send notifciations for points to rock
                        }


                        break;
                    }
                case R.id.q6yr30sbutton:
                    if(checked){
                        selectedAnswer = "Rap";
                        submitQ6button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //send notifications for points to rap
                        }



                    }

            }
        }
    };

    //set up notifications here
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


    public View.OnClickListener submitQ6Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(selectedAnswer == "Jazz"){
                //increment value in table for jazz
                genreViewModel.incrementJazz();
            } else if (selectedAnswer == "Country") {
                //increment value in table for country
                genreViewModel.incrementCountry();
            } else if (selectedAnswer == "Rock") {
                //increment value in table for rock
                genreViewModel.incrementRock();
            } else if (selectedAnswer == "Rap") {
                //increment value in table for Rap
                genreViewModel.incrementRap();
            }
            Intent spoilerintent = new Intent(Question6.this, Question7.class);
            spoilerintent.putExtra("SpoilerCheck", spoilerverifyer);
            startActivity(spoilerintent);
        }
    };


}