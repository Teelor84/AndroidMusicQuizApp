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

public class Question2 extends AppCompatActivity {

    private GenreViewModel genreViewModel = new GenreViewModel(getApplication());

    private RadioButton wealthbutton;
    private RadioButton emotionalsensbutton;
    private RadioButton familybutton;
    private RadioButton tastesbutton;
    private Button submitQ2button;
    int spoilerverifyer;

    String selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        Bundle extras = getIntent().getExtras();
        spoilerverifyer = extras.getInt("SpoilerCheck");

        wealthbutton = (RadioButton) findViewById(R.id.wealthbutton);
        emotionalsensbutton = (RadioButton) findViewById(R.id.esbutton);
        familybutton = (RadioButton) findViewById(R.id.familybutton);
        tastesbutton = (RadioButton) findViewById(R.id.tastesbutton);

        wealthbutton.setOnClickListener(Q2RadioButtonListener);
        emotionalsensbutton.setOnClickListener(Q2RadioButtonListener);
        familybutton.setOnClickListener(Q2RadioButtonListener);
        tastesbutton.setOnClickListener(Q2RadioButtonListener);

        submitQ2button = (Button) findViewById(R.id.submitq2button);
        submitQ2button.setOnClickListener(submitQ2Listener);
        submitQ2button.setEnabled(false);

    }


    private View.OnClickListener Q2RadioButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            switch(view.getId()){
                case R.id.wealthbutton:
                    if(checked){
                        selectedAnswer = "Rap";
                        submitQ2button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            //sendNotification for points to rap
                            sendNotification();
                        }


                        break;
                    }
                case R.id.esbutton:
                    if(checked){
                        selectedAnswer = "Rock";
                        submitQ2button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //send notifications for points to rock
                        }

                        break;
                    }
                case R.id.familybutton:
                    if(checked){
                        selectedAnswer = "Country";
                        submitQ2button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //send notifciations for points to country
                        }

                        break;
                    }
                case R.id.tastesbutton:
                    if(checked){
                        selectedAnswer = "Jazz";
                        submitQ2button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //send notifications for points to jazz
                        }

                        break;
                    }

            }
        }
    };


    //set up notfiications here
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


    public View.OnClickListener submitQ2Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(selectedAnswer == "Rap"){
                //increment value in table for rap
                genreViewModel.incrementRap();
            } else if (selectedAnswer == "Rock") {
                //increment value in table for rock
                genreViewModel.incrementRock();
            } else if (selectedAnswer == "Country") {
                //increment value in table for country
                genreViewModel.incrementCountry();
            } else if (selectedAnswer == "Jazz") {
                //increment value in table for jazz
                genreViewModel.incrementJazz();
            }
            Intent spoilerintent = new Intent(Question2.this, Question3.class);
            spoilerintent.putExtra("SpoilerCheck", spoilerverifyer);
            startActivity(spoilerintent);
        }
    };






}