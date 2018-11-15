package com.example.priyanshu.hangman_master.activities.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.priyanshu.hangman_master.R;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context fContext;

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "WordManager.db";
    private static final String TABLE_WORD = "word";

    //word table coulmns name
    private static final String COLUMN_WORD_ID = "id";
    private static final String COLUMN_WORD_NAME = "name";
    private static final String COLUMN_WORD_CATEGORY = "category";
    private static final String COLUMN_WORD_HINT = "hint";

    //create table sql query
    private String CREATE_WORD_TABLE = "CREATE TABLE " + TABLE_WORD + " ("
            + COLUMN_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_WORD_NAME + " TEXT,"
            + COLUMN_WORD_CATEGORY + " TEXT,"
            + COLUMN_WORD_HINT + " TEXT"
            + ")" ;

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_WORD;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        fContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORD_TABLE);
        initValues(db);
    }

    private void initValues(SQLiteDatabase db) {
       // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Resources res = fContext.getResources();
        String[] wordsList = res.getStringArray(R.array.words);

        for(String item : wordsList){
            String[] word = item.split(":");
            values.put(COLUMN_WORD_NAME, word[0]);
            values.put(COLUMN_WORD_CATEGORY, word[1]);
            values.put(COLUMN_WORD_HINT, word[2]);

            db.insert(TABLE_WORD, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);
    }

    //TODO: fill this method
    public String[] getWords(String category)
    {
        String[] columns = {
                COLUMN_WORD_NAME
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_WORD_CATEGORY + " = ?";
        String[] selectionArgs = {category};

        Cursor cursor = db.query(TABLE_WORD,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        ArrayList<String> wordsList = new ArrayList<String>();
        int count = cursor.getCount();
        while(cursor.moveToNext()){
            String data = cursor.getString(cursor.getColumnIndex("name"));
            wordsList.add(data);
        }
        String[] words = new String[wordsList.size()];
        words = wordsList.toArray(words);
        return words;
    }

    public String[] getCategories()
    {
       // initValues();

        String[] columns = {
                COLUMN_WORD_CATEGORY
        };

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true,
                TABLE_WORD,
                columns,
                null,
                null,
                COLUMN_WORD_CATEGORY,
                null,
                null,
                null);

        ArrayList<String> categoriesList = new ArrayList<String>();
        int count = cursor.getCount();
        while(cursor.moveToNext()){
                String data = cursor.getString(cursor.getColumnIndex("category"));
                categoriesList.add(data);
        }
        String[] categories = new String[categoriesList.size()];
        categories = categoriesList.toArray(categories);
        return categories;
    }

    public String getHint(String word){
        String[] columns = {
                COLUMN_WORD_HINT
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_WORD_NAME + " = ?";
        String[] selectionArgs = {word};

        Cursor cursor = db.query(TABLE_WORD,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        ArrayList<String> hintList = new ArrayList<String>();
        int count = cursor.getCount();
        while(cursor.moveToNext()){
            String data = cursor.getString(cursor.getColumnIndex("hint"));
            hintList.add(data);
        }
        String[] words = new String[hintList.size()];
        words = hintList.toArray(words);
        return words[0];
    }
}
