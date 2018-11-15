package com.example.priyanshu.hangman_master.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.priyanshu.hangman_master.R;

public class StartGameActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_play;
    private TextView categorySelected;
    private String[] categories = {"a", "b", "c"};
    private String choosenCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        getSupportActionBar().hide();

        initViews();
    }

    private void initViews() {
        button_play = (Button)findViewById(R.id.button_play);
        categorySelected = (TextView)findViewById(R.id.menu_category);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.menu_category:
                chooseCategory();
                break;
            case R.id.button_play:
                Intent intentRegister = new Intent(getApplicationContext(), HangmanActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void chooseCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(categories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choosenCategory = categories[which];
                categorySelected.setText(choosenCategory);
            }
        });
        builder.show();
    }
}
