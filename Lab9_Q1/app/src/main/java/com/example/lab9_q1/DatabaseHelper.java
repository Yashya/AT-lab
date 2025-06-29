package com.example.lab9_q1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Survey.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SURVEY = "survey";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ANSWER = "answer";
    public static final String COLUMN_COMMENTS = "comments";

    private static final String SQL_CREATE_SURVEY_TABLE =
            "CREATE TABLE " + TABLE_SURVEY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ANSWER + " TEXT," +
                    COLUMN_COMMENTS + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SURVEY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY);
        onCreate(db);
    }
}