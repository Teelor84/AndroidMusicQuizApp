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

public class Question3 extends AppCompatActivity {

    private GenreViewModel genreViewModel = new GenreViewModel(getApplication());

    private RadioButton yesQ3button;
    private RadioButton noQ3button;
    private Button submitQ3button;

    String selectedAnswer;

    int spoilerverifyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        Bundle extras = getIntent().getExtras();
        spoilerverifyer = extras.getInt("SpoilerCheck");

        yesQ3button = (RadioButton) findViewById(R.id.q3yesbutton);
        noQ3button = (RadioButton) findViewById(R.id.q3nobutton);

        yesQ3button.setOnClickListener(Q3RadioButtonListener);
        noQ3button.setOnClickListener(Q3RadioButtonListener);

        submitQ3button = (Button) findViewById(R.id.submitq3button);
        submitQ3button.setOnClickListener(submitQ3Listener);
        submitQ3button.setEnabled(false);
    }


    private View.OnClickListener Q3RadioButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            switch(view.getId()){
                case R.id.q3yesbutton:
                    if(checked){
                        selectedAnswer = "Rock and Rap";
                        submitQ3button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //sendNotification for points to rock and rap
                        }


                        break;
                    }
                case R.id.q3nobutton:
                    if(checked){
                        selectedAnswer = "Country and Jazz";
                        submitQ3button.setEnabled(true);
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




    public View.OnClickListener submitQ3Listener = new View.OnClickListener() {
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
            Intent spoilerintent = new Intent(Question3.this, Question4.class);
            spoilerintent.putExtra("SpoilerCheck", spoilerverifyer);
            startActivity(spoilerintent);
        }
    };

}