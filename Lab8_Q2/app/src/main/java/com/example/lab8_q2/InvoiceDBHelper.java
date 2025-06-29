package com.example.lab8_q2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InvoiceDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Invoice.db";
    private static final int DATABASE_VERSION = 1;

    public InvoiceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_INVOICE_TABLE = "CREATE TABLE " +
                InvoiceContract.InvoiceEntry.TABLE_NAME + " (" +
                InvoiceContract.InvoiceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InvoiceContract.InvoiceEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                InvoiceContract.InvoiceEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                InvoiceContract.InvoiceEntry.COLUMN_PRICE + " REAL NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_INVOICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + InvoiceContract.InvoiceEntry.TABLE_NAME);
        onCreate(db);
    }
}