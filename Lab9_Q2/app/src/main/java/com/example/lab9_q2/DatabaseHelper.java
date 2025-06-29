package com.example.lab9_q2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AirlineReservations.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ORIGIN = "origin";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PRICE = "price";

    private static final String SQL_CREATE_RESERVATIONS_TABLE =
            "CREATE TABLE " + TABLE_RESERVATIONS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ORIGIN + " TEXT," +
                    COLUMN_DESTINATION + " TEXT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_PRICE + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RESERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        onCreate(db);
    }
}