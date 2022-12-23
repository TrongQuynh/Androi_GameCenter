package com.example.gamecenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenter.DB.DB_Handle;
import com.example.gamecenter.DB.Game;
import com.example.gamecenter.DB.ScoreGame;
import com.example.gamecenter.config.TimeRemider;
import com.example.gamecenter.games.MyService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        if(!isServiceRunning(MyService.class)){
            runMusic();
        }

        openGame();
        turnOnOffMusic();

        createAlarmManagerChannel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(MainActivity.this, ReminderNotification.class);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent,PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtCloseApp = System.currentTimeMillis();
        long timeAtToReminder = 1000 * TimeRemider.getTimeRemider(); // 10 seconds

        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + timeAtToReminder, pendingIntent);
        Toast.makeText(this, "On Destroy", Toast.LENGTH_SHORT).show();
    }

    private void runMusic(){
        try {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void turnOnOffMusic(){
        try {
            ((ImageView) findViewById(R.id.btn_Volume)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startService(new Intent(MainActivity.this, MyService.class));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openGame(){
        final ImageView ticTacToe = (ImageView) findViewById(R.id.game_tictactoe);
        final ImageView flipCard = (ImageView) findViewById(R.id.game_flipcard);
        final ImageView twoZeroFourEight = (ImageView) findViewById(R.id.game_2048);

        flipCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(MainActivity.this, FlipCardMemory_Menu.class);
                    startActivity(i);
                }catch (Exception e ){

                }
            }
        });

        ticTacToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(MainActivity.this, TicTacToe_AddPlayer.class);
                    startActivity(i);
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });

        twoZeroFourEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(MainActivity.this, TwoZeroFourEight.class);
                    startActivity(i);
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });
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

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(MainActivity.this, MyService.class));
    }

    @Override
    public void onBackPressed() {

        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            builder.setTitle("Game Center");
            builder.setIcon(R.drawable.logo_game);
            builder.setMessage("Do you want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startActivity(startMain);
                            stopService(new Intent(MainActivity.this, MyService.class));
                            finishAndRemoveTask();
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
}