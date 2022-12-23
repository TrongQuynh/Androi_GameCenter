package com.example.gamecenter;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamecenter.config.TimeRemider;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends AppCompatActivity {
    private static final int NOTIFICATION = 1;
    private final List<int[]> combinationsList = new ArrayList<>();

    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int playerTurn = 1;

    private int totalSelectedBoxes = 1;

    private LinearLayout playerOneLayout, playerTwoLayout;
    private TextView playerOneName, playerTwoName;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }

        setContentView(R.layout.tictactoe);
        createAlarmManagerChannel();

        playerOneName = findViewById(R.id.playerOneName);
        playerTwoName = findViewById(R.id.playerTwoName);

        playerOneLayout = findViewById(R.id.playerOneLayout);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        combinationsList.add(new int[]{0, 1, 2});
        combinationsList.add(new int[]{3, 4, 5});
        combinationsList.add(new int[]{6, 7, 8});
        combinationsList.add(new int[]{0, 3, 6});
        combinationsList.add(new int[]{1, 4, 7});
        combinationsList.add(new int[]{2, 5, 8});
        combinationsList.add(new int[]{2, 4, 6});
        combinationsList.add(new int[]{0, 4, 8});

        final String getPlayerOneName = getIntent().getStringExtra("playerOne");
        final String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        playerOneName.setText(getPlayerOneName);
        playerTwoName.setText(getPlayerTwoName);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(0)) {
                    performAction((ImageView) v, 0);
                }
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(1)) {
                    performAction((ImageView) v, 1);
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(2)) {
                    performAction((ImageView) v, 2);
                }
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(3)) {
                    performAction((ImageView) v, 3);
                }
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(4)) {
                    performAction((ImageView) v, 4);
                }
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(5)) {
                    performAction((ImageView) v, 5);
                }
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(6)) {
                    performAction((ImageView) v, 6);
                }
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(7)) {
                    performAction((ImageView) v, 7);
                }
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBoxSelectable(8)) {
                    performAction((ImageView) v, 8);
                }
            }
        });

    }




    private void performAction(ImageView imageView, int selectedBoxPosition) {

        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.cross);

            if (checkPlayerWin()) {
                TicTacToe_Win_Dialog winDialog = new TicTacToe_Win_Dialog(TicTacToe.this, playerOneName.getText().toString() + " has won the match", TicTacToe.this);
                winDialog.setCancelable(false);
                winDialog.show();
            } else if (totalSelectedBoxes == 9) {
                TicTacToe_Win_Dialog winDialog = new TicTacToe_Win_Dialog(this, "It is a draw!", this);
                winDialog.setCancelable(false);
                winDialog.show();
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;
            }
        } else {
            imageView.setImageResource(R.drawable.zero);
            if (checkPlayerWin()) {
                TicTacToe_Win_Dialog winDialog = new TicTacToe_Win_Dialog(this, playerTwoName.getText().toString() + " has won the match", this);
                winDialog.setCancelable(false);
                winDialog.show();
            } else if (selectedBoxPosition == 9) {
                TicTacToe_Win_Dialog winDialog = new TicTacToe_Win_Dialog(this, "It is a draw!", this);
                winDialog.setCancelable(false);
                winDialog.show();
            } else {
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }
        }
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            playerOneLayout.setBackgroundResource(R.drawable.back_blue_border);
            playerTwoLayout.setBackgroundResource(R.drawable.back_dark_blue);
        } else {
            playerTwoLayout.setBackgroundResource(R.drawable.back_blue_border);
            playerOneLayout.setBackgroundResource(R.drawable.back_dark_blue);
        }
    }

    private boolean checkPlayerWin() {
        boolean response = false;
        for (int i = 0; i < combinationsList.size(); i++) {
            final int[] combination = combinationsList.get(i);

            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn && boxPositions[combination[2]] == playerTurn) {
                response = true;
            }
        }

        return response;
    }

    private boolean isBoxSelectable(int boxPosition) {

        boolean response = false;
        if (boxPositions[boxPosition] == 0) {
            response = true;
        }
        return response;
    }

    public void restartMatch() {
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerTurn = 1;
        totalSelectedBoxes = 1;
        image1.setImageResource(R.drawable.transparent);
        image2.setImageResource(R.drawable.transparent);
        image3.setImageResource(R.drawable.transparent);
        image4.setImageResource(R.drawable.transparent);
        image5.setImageResource(R.drawable.transparent);
        image6.setImageResource(R.drawable.transparent);
        image7.setImageResource(R.drawable.transparent);
        image8.setImageResource(R.drawable.transparent);
        image9.setImageResource(R.drawable.transparent);
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

    @Override
    public void onBackPressed() {
        try {
            Intent i = new Intent(this, TicTacToe_AddPlayer.class);
            startActivity(i);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
