package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.gamecenter.config.TimeRemider;

public class TwoZeroFourEight extends AppCompatActivity {

    private Button playgame, choitiep ;


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            Intent startMain = new Intent(Intent.ACTION_MAIN);
//                            startMain.addCategory(Intent.CATEGORY_HOME);
//                            startActivity(startMain);
//                            finish();
                            try {
                                Intent i = new Intent(TwoZeroFourEight.this, MainActivity.class);
                                startActivity(i);
                            }catch (Exception e ){
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).show();
        }catch (Exception e)
        {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_two_zero_four_eight);
        anhxa();
        batxukienclick();
        createAlarmManagerChannel();
    }

    private void createAlarmManagerChannel(){
        // This notification will be called after a few minute close app

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String channelID = "AlarmManager";
            String description = "This notification will be called after a few minute close app";

            NotificationChannel channel = new NotificationChannel(channelID, "Game Center Channel", importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, ReminderNotification.class);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent,PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtCloseApp = System.currentTimeMillis();
        long timeAtToReminder = 1000 * TimeRemider.getTimeRemider(); // 10 seconds

        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + timeAtToReminder, pendingIntent);
        Toast.makeText(this, "On Destroy", Toast.LENGTH_SHORT).show();
    }

    public void batxukienclick()
    {
        playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoZeroFourEight.this, play2048.class);
                intent.putExtra("playgame", 1);
                startActivity(intent);
            }
        });
        choitiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoZeroFourEight.this, play2048.class);
                intent.putExtra("playgame", 3);
                startActivity(intent);
            }
        });

    }


    public void anhxa()
    {
        playgame = (Button)findViewById(R.id.playgame2048);
        choitiep = (Button)findViewById(R.id.choitiep);
    }
}