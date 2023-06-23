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

public class Question4 extends AppCompatActivity {

    private GenreViewModel genreViewModel = new GenreViewModel(getApplication());

    private RadioButton loudQ4button;
    private RadioButton softQ4button;
    private Button submitQ4button;

    String selectedAnswer;

    int spoilerverifyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);


        Bundle extras = getIntent().getExtras();
        spoilerverifyer = extras.getInt("SpoilerCheck");

        loudQ4button = (RadioButton) findViewById(R.id.q4loudbutton);
        softQ4button = (RadioButton) findViewById(R.id.q4softbutton);

        loudQ4button.setOnClickListener(Q4RadioButtonListener);
        softQ4button.setOnClickListener(Q4RadioButtonListener);

        submitQ4button = (Button) findViewById(R.id.submitq4button);
        submitQ4button.setOnClickListener(submitQ4Listener);
        submitQ4button.setEnabled(false);
    }


    private View.OnClickListener Q4RadioButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            switch(view.getId()){
                case R.id.q4loudbutton:
                    if(checked){
                        submitQ4button.setEnabled(true);
                        selectedAnswer = "Rock and Rap";
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //sendNotification for points to rock and rap
                        }


                        break;
                    }
                case R.id.q4softbutton:
                    if(checked){
                        selectedAnswer = "Country and Jazz";
                        submitQ4button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //send notifications for points to rock
                        }
                        break;
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

    public View.OnClickListener submitQ4Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(selectedAnswer == "Rock and Rap"){
                //increment values in table for rock and rap
                genreViewModel.incrementRock();
                genreViewModel.incrementRap();
            } else if (selectedAnswer == "Country and Jazz") {
                //increment value in table for country and jazz
                genreViewModel.incrementCountry();
                genreViewModel.incrementJazz();
            }
            Intent spoilerintent = new Intent(Question4.this, Question5.class);
            spoilerintent.putExtra("SpoilerCheck", spoilerverifyer);
            startActivity(spoilerintent);
        }
    };
}