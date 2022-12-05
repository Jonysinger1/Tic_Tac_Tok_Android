package com.example.tictactok;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private ImageView win036, win147, win258, win012, win345, win678, win048, win246;
    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    private Button [] buttons = new Button[9];
    private Button resetGame,playagain;
    private boolean activePlayer;

    //p1 => 0
    //p2 => 1
    //empty => 2
     int [] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //initially all are empty
        int [][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        resetGame = (Button) findViewById(R.id.resetGame);
        playagain = (Button) findViewById(R.id.playagain);
        win036 = (ImageView) findViewById(R.id.win036);
        win147 = (ImageView) findViewById(R.id.win147);
        win258 = (ImageView) findViewById(R.id.win258);
        win012 = (ImageView) findViewById(R.id.win012);
        win345 = (ImageView) findViewById(R.id.win345);
        win678 = (ImageView) findViewById(R.id.win678);
        win048 = (ImageView) findViewById(R.id.win048);
        win246 = (ImageView) findViewById(R.id.win246);




        for(int i = 0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resID);
            buttons[i].setOnClickListener(this);
        }
        resetGame.setOnClickListener(this);
        playagain.setOnClickListener(this);
        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;


    }

    @Override
    public void onClick(View view) {
        //reset game
        if(view.getId() == R.id.resetGame){
            resetGame();
        }
        //play again
        if(view.getId() == R.id.playagain){
            playagain();
        }
        if(!((Button) view).getText().toString().equals("")){
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId()); //btn_0
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); //0
        playerStatus.setText("Player One Turn");
        if(activePlayer){
            playerStatus.setText("Player Two Turn");
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FF0000"));
            gameState[gameStatePointer] = 0;
        }else{
            playerStatus.setText("Player One Turn");
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#0000FF"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;
        if(checkWinner()){
            if(activePlayer){ //player 1 won
                playerOneScoreCount++;

                updatePlayerScore();
                playerStatus.setText("Player 1 Won!");
//                resetBoard();
            }else{
                playerTwoScoreCount++;
                updatePlayerScore();
                playerStatus.setText("Player 2 Won!");
               // resetBoard();
            }
        }else if(roundCount == 9){
            playerStatus.setText("Draw!");
            //resetBoard();
        }else{
            activePlayer = !activePlayer;
        }
        if(activePlayer){
//            playerStatus.setText("Player One Turn");
        }else{
//            playerStatus.setText("Player Two Turn");

        }

    }

    private void playagain() {
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setText("");
        }
        roundCount = 0;
        activePlayer = true;
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }
        win036.setVisibility(View.INVISIBLE);
        win147.setVisibility(View.INVISIBLE);
        win258.setVisibility(View.INVISIBLE);
        win012.setVisibility(View.INVISIBLE);
        win345.setVisibility(View.INVISIBLE);
        win678.setVisibility(View.INVISIBLE);
        win048.setVisibility(View.INVISIBLE);
        win246.setVisibility(View.INVISIBLE);
        playerStatus.setText("Player One Turn");

        for(int i = 0; i < buttons.length; i++){
            buttons[i].setEnabled(true);
        }
    }

    private void resetGame() {
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        win036.setVisibility(View.INVISIBLE);
        win147.setVisibility(View.INVISIBLE);
        win258.setVisibility(View.INVISIBLE);
        win012.setVisibility(View.INVISIBLE);
        win345.setVisibility(View.INVISIBLE);
        win678.setVisibility(View.INVISIBLE);
        win048.setVisibility(View.INVISIBLE);
        win246.setVisibility(View.INVISIBLE);

        updatePlayerScore();
        playagain();
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setEnabled(true);
        }
    }


    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    private boolean checkWinner() {
        boolean winnerResult = false;
        for(int [] winPosition : winPositions){
            if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2){
                winnerResult = true;
            }
        }
        if (winnerResult){

            for(int i = 0; i < buttons.length; i++){
                buttons[i].setEnabled(false);
            }
        }
        //if 036 win so 036 will be visible
        if(gameState[0] == gameState[3] && gameState[3] == gameState[6] && gameState[0] != 2){
            win036.setVisibility(View.VISIBLE);
        }
        //if 147 win so 147 will be visible
        if(gameState[1] == gameState[4] && gameState[4] == gameState[7] && gameState[1] != 2){
            win147.setVisibility(View.VISIBLE);
        }
        //if 258 win so 258 will be visible
        if(gameState[2] == gameState[5] && gameState[5] == gameState[8] && gameState[2] != 2){
            win258.setVisibility(View.VISIBLE);
        }
        //if 012 win so 012 will be visible
        if(gameState[0] == gameState[1] && gameState[1] == gameState[2] && gameState[0] != 2){
            win012.setVisibility(View.VISIBLE);
        }
        //if 345 win so 345 will be visible
        if(gameState[3] == gameState[4] && gameState[4] == gameState[5] && gameState[3] != 2){
            win345.setVisibility(View.VISIBLE);
        }
        //if 678 win so 678 will be visible
        if(gameState[6] == gameState[7] && gameState[7] == gameState[8] && gameState[6] != 2){
            win678.setVisibility(View.VISIBLE);
        }
        //if 048 win so 048 will be visible
        if(gameState[0] == gameState[4] && gameState[4] == gameState[8] && gameState[0] != 2){
            win048.setVisibility(View.VISIBLE);
        }
        //if 246 win so 246 will be visible
        if(gameState[2] == gameState[4] && gameState[4] == gameState[6] && gameState[2] != 2){
            win246.setVisibility(View.VISIBLE);
        }
        return winnerResult;
    }


}