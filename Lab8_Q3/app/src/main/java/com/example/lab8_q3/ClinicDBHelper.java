package com.example.lab8_q3;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClinicDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clinic.db";
    private static final int DATABASE_VERSION = 1;

    public ClinicDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_APPOINTMENT_TABLE = "CREATE TABLE " +
                ClinicContract.AppointmentEntry.TABLE_NAME + " (" +
                ClinicContract.AppointmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ClinicContract.AppointmentEntry.COLUMN_PATIENT_NAME + " TEXT NOT NULL, " +
                ClinicContract.AppointmentEntry.COLUMN_DOCTOR_NAME + " TEXT NOT NULL, " +
                ClinicContract.AppointmentEntry.COLUMN_APPOINTMENT_DATE + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ClinicContract.AppointmentEntry.TABLE_NAME);
        onCreate(db);
    }
}