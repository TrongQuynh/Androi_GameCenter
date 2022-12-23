package com.example.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TicTacToe_AddPlayer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_addplayer);

        getSupportActionBar().hide();

        final EditText playerOne = findViewById(R.id.txtPlayer1);
        final EditText playerTwo = findViewById(R.id.txtPlayer2);
        final Button startGameBtn = findViewById(R.id.btnStart);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getPlayerOneName = playerOne.getText().toString();
                final String getPlayerTwoName = playerTwo.getText().toString();

                if(getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()){
                    Toast.makeText(TicTacToe_AddPlayer.this, "Please enter players names", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(TicTacToe_AddPlayer.this, TicTacToe.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", getPlayerTwoName);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        try {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}
