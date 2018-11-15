package com.example.priyanshu.hangman_master.activities.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.priyanshu.hangman_master.R;
import com.example.priyanshu.hangman_master.activities.sql.DatabaseHelper;

public class StartGameActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_play;
    private Button button_choose_category;
    private TextView text_category;
    private String[] categories = {"a", "b", "c"};
    private String choosenCategory;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        getSupportActionBar().hide();

        initViews();
        initObjects();
        initListeners();
    }

    private void initViews() {
        button_play = (Button)findViewById(R.id.button_play);
        button_choose_category = (Button)findViewById(R.id.button_choose_category);
        text_category = (TextView)findViewById(R.id.text_category);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(StartGameActivity.this);
        categories = databaseHelper.getCategories();
        choosenCategory = categories.equals(null)?"":categories[0];
        text_category.setText(choosenCategory);
    }

    private void initListeners() {
        button_play.setOnClickListener(this);
        button_choose_category.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_choose_category:
                chooseCategory();
                //text_category.setText(choosenCategory);
                break;
            case R.id.button_play:
                Intent intentRegister = new Intent(getApplicationContext(), HangmanActivity.class);
                intentRegister.putExtra("category",choosenCategory);
                startActivity(intentRegister);
                break;
        }
    }


    private void chooseCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose a category");
        builder.setItems(categories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choosenCategory=categories[which];
                text_category.setText(choosenCategory);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
