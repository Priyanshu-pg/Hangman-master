package com.example.priyanshu.hangman_master.activities.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.priyanshu.hangman_master.R;
import com.example.priyanshu.hangman_master.activities.sql.DatabaseHelper;
import com.example.priyanshu.hangman_master.activities.Helper.LetterAdapter;
import java.util.Random;

import static android.app.PendingIntent.getActivity;

public class HangmanActivity extends AppCompatActivity implements View.OnClickListener{
    private String[] words;
    private Random rand;
    private String currWord;
    private String category;
    private boolean isHintUsed;
    private int score;
    private int record;

    private DatabaseHelper databaseHelper;
    private LetterAdapter ltrAdapt;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor prefEditor;
    private String sharedPrefRecordFile = "com.example.priyanshu.hangman_master.record";

    private LinearLayout wordLayout;
    private TextView[] charViews;
    private GridView letters;
    private ImageView[] bodyParts;
    private Button button_hint;
    private TextView text_score;
    private TextView text_record;
    //number of body parts
    private int numParts=6;
    //current part - will increment when wrong answers are chosen
    private int currPart;
    //number of characters in current word
    private int numChars;
    //number correctly guessed
    private int numCorr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        getSupportActionBar().hide();

        initViews();
        initObjects();

        playGame();
    }
    private void initViews() {
        wordLayout = (LinearLayout)findViewById(R.id.word);
        letters = (GridView)findViewById(R.id.letters);

        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView)findViewById(R.id.head);
        bodyParts[1] = (ImageView)findViewById(R.id.body);
        bodyParts[2] = (ImageView)findViewById(R.id.arm1);
        bodyParts[3] = (ImageView)findViewById(R.id.arm2);
        bodyParts[4] = (ImageView)findViewById(R.id.leg1);
        bodyParts[5] = (ImageView)findViewById(R.id.leg2);

        button_hint = (Button)findViewById(R.id.button_hint);
        text_score = (TextView) findViewById(R.id.text_score);
        text_record = (TextView) findViewById(R.id.text_edit_record);

        button_hint.setOnClickListener(this);
    }
    private void initObjects() {
        databaseHelper = new DatabaseHelper(HangmanActivity.this);
        Resources res = getResources();
        category = getIntent().getStringExtra("category");//TODO: get category from startGame activity
        words = databaseHelper.getWords(category);
        rand = new Random();
        currWord = "";
        isHintUsed = false;
        score = Integer.valueOf(text_score.getText().toString());

        sharedPref = getSharedPreferences(sharedPrefRecordFile, MODE_PRIVATE);
        record = sharedPref.getInt(getString(R.string.RECORD_KEY), 0);

        prefEditor = sharedPref.edit();
        record = Math.max(score, record);
        prefEditor.putInt(getString(R.string.RECORD_KEY), record);
        prefEditor.apply();

        text_record.setText(String.valueOf(record));
    }

    private void playGame() {
        String newWord = words[rand.nextInt(words.length)];
        while (newWord.equals(currWord))  {
            newWord = words[rand.nextInt(words.length)];
        }
        currWord = newWord;

        charViews = new TextView[currWord.length()];
        wordLayout.removeAllViews();

        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(this);
            charViews[c].setText(""+currWord.charAt(c));

            charViews[c].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);
            //add to layout
            wordLayout.addView(charViews[c]);

        }
        ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);

        currPart=0;
        numChars=currWord.length();
        numCorr=0;

        for(int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }

    }

    //TODO: maintain score
    public void letterPressed(View view) {
        //user has pressed a letter to guess
        String ltr=((TextView)view).getText().toString();
        char letterChar = ltr.charAt(0);
        // merkitaan arvatuksi
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);

        // tarkistetaan onko arvattavassa sanassa
        boolean correct = false;
        for(int k = 0; k < currWord.length(); k++) {
            if (Character.toUpperCase(currWord.charAt(k))==Character.toUpperCase(letterChar)){
                correct = true;
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }

        if (correct) {
            //correct guess
            score+=10;
            text_score.setText(String.valueOf(score));
            if (numCorr == numChars) {
                score+=100;
                text_score.setText(String.valueOf(score));
                //user has won
                // Disable Buttons
                disableBtns();

                // Display Alert Dialog
                AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
                winBuild.setTitle("Yay, well done!");
                winBuild.setMessage("You won!\n\nThe answer was:\n\n"+currWord+"\nScore: "+score);
                winBuild.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                updateRecord();
                                HangmanActivity.this.playGame();
                            }});

                winBuild.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                updateRecord();
                                HangmanActivity.this.finish();
                            }});

                winBuild.show();
            }
        } else if (currPart < numParts) {
            //some guesses left
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        } else {
            //user has lost
            disableBtns();

            // Display Alert Dialog
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("Oopsie");
            loseBuild.setMessage("You lose!\n\nThe answer was:\n\n"+currWord+"\nScore: "+score);
            loseBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            updateRecord();
                            HangmanActivity.this.playGame();
                        }});

            loseBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            updateRecord();
                            HangmanActivity.this.finish();
                        }});

            loseBuild.show();

        }
    }

    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_hint:
                if(!isHintUsed){
                    isHintUsed = true;
                    score-=5;
                    text_score.setText(String.valueOf(score));
                }
                showHint();
        }
    }

    private void showHint() {
        AlertDialog.Builder helpBuild = new AlertDialog.Builder(this);
        helpBuild.setTitle("Hint");
        String hintMessage = databaseHelper.getHint(currWord);
        helpBuild.setMessage(hintMessage)
        .setNeutralButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        helpBuild.show();
    }

    private void updateRecord()
    {
        record = Math.max(score, record);
        prefEditor.putInt(getString(R.string.RECORD_KEY), record);
        prefEditor.apply();

        text_record.setText(String.valueOf(record));
    }
}
