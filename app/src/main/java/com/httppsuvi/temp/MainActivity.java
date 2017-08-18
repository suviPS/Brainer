package com.httppsuvi.temp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private TextView timerTextView;
    private TextView problemTextView;
    private TextView scoreTextView;
    private TextView displayFinalScoreTextView;
    private Button startButton;
    private CountDownTimer timer;
    private GridLayout gridLayout;
    private Button buttonAbouth;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        timerTextView = (TextView) findViewById(R.id.timertextView);
        problemTextView = (TextView) findViewById(R.id.problemTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        startButton = (Button) findViewById(R.id.startButton);
        displayFinalScoreTextView = (TextView) findViewById(R.id.displayFinalScoreTextView);
        gridLayout = (GridLayout) findViewById(R.id.gridlayout1);
        buttonAbouth = (Button) findViewById(R.id.buttonAbout);

        problemTextView.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.INVISIBLE);
        displayFinalScoreTextView.setVisibility(View.INVISIBLE);


    }


    private int timerDefault = 30000;

    public void startGame(View v) {

        /////////////
        game= new Game();
        //////////////

        v.setVisibility(View.INVISIBLE);
        buttonAbouth.setVisibility(View.INVISIBLE);
        problemTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);

        //cleanUp views from last play
        for (int i = 0; i < 4; i++) {
            gridLayout.getChildAt(i).setClickable(true);
        }
        scoreTries = 0;
        scoreHit = 0;
        scoreTextView.setText("0/0");
        displayFinalScoreTextView.setVisibility(View.INVISIBLE);

        //start timer
        timer = new CountDownTimer(timerDefault + 300, 1000) {
            @Override
            public void onTick(long timeRemain) {
                timerTextView.setText(String.valueOf(timeRemain / 1000));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Time's up!");
                //stop game, display score, display start button

                for (int i = 0; i < 4; i++) {
                    gridLayout.getChildAt(i).setClickable(false);
                }

                displayFinalScoreTextView.setText("Your score: " + scoreHit + " out of " + scoreTries);
                displayFinalScoreTextView.setVisibility(View.VISIBLE);

                startButton.setVisibility(View.VISIBLE);
                buttonAbouth.setVisibility(View.VISIBLE);


            }
        }.start();

        playGame();

    }



    private ArrayList<Integer> arrayList;
    private Button tempButton;

    //Game's Logic
    public void playGame() {

        game.playRound();
        arrayList= game.getArrayList();

        //update views
        problemTextView.setText(game.getProblem());
        for (int i = 0; i < 4; i++) {
            tempButton = (Button) gridLayout.getChildAt(i);
            //tempButton.setText("a "+i);
            tempButton.setText(Integer.toString(arrayList.get(i)));
        }
    }


    private int scoreTries = 0, scoreHit = 0;
    private Button tempButton2;

    public void answerGiven(View v) {

        tempButton2 = (Button) v;
        if (tempButton2.getText().toString().equals(
                Integer.toString(game.getResult()))) {
            //hit
            scoreHit++;
        }
        scoreTries++;
        scoreTextView.setText( scoreHit+ "/" +  scoreTries);
        playGame();

    }


    static final String SCORE_TRIES = "scoreTries";
    static final String SCORE_HIT = "scoreHit";
    static final String TIMER_DEFAULT = "timerDefault";
    static final String DISPLAY_FINAL_SCORE= "displayFinalScoreTextView";

    @Override
    public void onSaveInstanceState(Bundle outState) {


        outState.putInt(SCORE_TRIES, scoreTries);
        outState.putInt(SCORE_HIT, scoreHit);
        //timerDefault:
        try {
            outState.putInt(TIMER_DEFAULT, Integer.parseInt(timerTextView.getText().toString()));
        } catch(NumberFormatException ex){
            //it says "Time's up!"
            outState.putInt(TIMER_DEFAULT,0);
        }

        outState.putString(DISPLAY_FINAL_SCORE, displayFinalScoreTextView.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
           if (displayFinalScoreTextView.getVisibility() == View.INVISIBLE) {

                timerDefault = savedInstanceState.getInt(TIMER_DEFAULT);
                timerDefault *= 1000;
                startGame(findViewById(R.id.startButton));
                timerDefault=30000;

                scoreTries = savedInstanceState.getInt(SCORE_TRIES);
                scoreHit = savedInstanceState.getInt(SCORE_HIT);

                scoreTextView.setText(scoreHit+ "/" +  scoreTries);
           }
//            else{
//                //done automatically :P
//                //displayFinalScoreTextView.setText(savedInstanceState.getString(DISPLAY_FINAL_SCORE));
//            }
        }
    }

    public void aboutClicked(View v){

        new AlertDialog.Builder(this)
                .setTitle("\tAbout")
                .setMessage("Created by \tPetar Suvajac")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(R.drawable.brainer_ico)
                .show();

    }
}
