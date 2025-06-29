package com.example.lab7_q1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 1;

    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE " +
                StudentContract.StudentEntry.TABLE_NAME + " (" +
                StudentContract.StudentEntry.COLUMN_ROLL_NUMBER + " TEXT PRIMARY KEY, " +
                StudentContract.StudentEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                StudentContract.StudentEntry.COLUMN_MARKS + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StudentContract.StudentEntry.TABLE_NAME);
        onCreate(db);
    }
}
