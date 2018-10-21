package com.example.priyanshu.hangman_master.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.priyanshu.hangman_master.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton buttonHelp;
    private Button buttonStartGame;
    private Button buttonScoreboard;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        getSupportActionBar().hide();
        
        initViews();
        initListeners();
    }

    private void initViews() {
        buttonHelp = (FloatingActionButton) findViewById(R.id.button_help);
        buttonStartGame = (Button)findViewById(R.id.button_start_game);
        buttonScoreboard = (Button)findViewById(R.id.button_scoreboard);
    }

    private void initListeners() {
        buttonHelp.setOnClickListener(this);
        buttonStartGame.setOnClickListener(this);
        buttonScoreboard.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_help:
                openHelpWindow();
                break;
            case R.id.button_scoreboard:
                openScoreboardWindow();
                break;
            case R.id.button_start_game:
                startgame();
                break;
        }

    }

    private void startgame() {
        Intent intentRegister = new Intent(getApplicationContext(), StartGameActivity.class);
        startActivity(intentRegister);
    }

    private void openScoreboardWindow() {
        Intent intentRegister = new Intent(getApplicationContext(), ScoreboardActivity.class);
        startActivity(intentRegister);
    }

    private void openHelpWindow() {
        startActivity(new Intent(WelcomeActivity.this, HelpPopupActivity.class));
    }
}
