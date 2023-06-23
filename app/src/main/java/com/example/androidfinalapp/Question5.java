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

public class Question5 extends AppCompatActivity {

    private GenreViewModel genreViewModel = new GenreViewModel(getApplication());

    private RadioButton fastQ5button;
    private RadioButton slowQ5button;
    private Button submitQ5button;

    String selectedAnswer;

    int spoilerverifyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        Bundle extras = getIntent().getExtras();
        spoilerverifyer = extras.getInt("SpoilerCheck");

        fastQ5button = (RadioButton) findViewById(R.id.q5fastbutton);
        slowQ5button = (RadioButton) findViewById(R.id.q5slowbutton);

        fastQ5button.setOnClickListener(Q5RadioButtonListener);
        slowQ5button.setOnClickListener(Q5RadioButtonListener);

        submitQ5button = (Button) findViewById(R.id.submitq5button);
        submitQ5button.setOnClickListener(submitQ5Listener);
        submitQ5button.setEnabled(false);
    }


    private View.OnClickListener Q5RadioButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            switch(view.getId()){
                case R.id.q5fastbutton:
                    if(checked){
                        selectedAnswer = "Rap and Jazz";
                        submitQ5button.setEnabled(true);
                        if(spoilerverifyer == 1){
                            sendNotification();
                            //sendNotification for points to rock and rap
                        }


                        break;
                    }
                case R.id.q5slowbutton:
                    if(checked){
                        selectedAnswer = "Rock and Country";
                        submitQ5button.setEnabled(true);
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


    public View.OnClickListener submitQ5Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(selectedAnswer == "Rap and Jazz"){
                //increment values in table for rap and jazz
                genreViewModel.incrementRap();
                genreViewModel.incrementJazz();
            } else if (selectedAnswer == "Rock and Country") {
                //increment value in table for rock and country
                genreViewModel.incrementRock();
                genreViewModel.incrementCountry();
            } else {
                
            }
            Intent spoilerintent = new Intent(Question5.this, Question6.class);
            spoilerintent.putExtra("SpoilerCheck", spoilerverifyer);
            startActivity(spoilerintent);
        }
    };

}