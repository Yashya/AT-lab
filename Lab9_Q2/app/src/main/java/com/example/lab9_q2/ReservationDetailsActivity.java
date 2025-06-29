package com.example.lab9_q2;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReservationDetailsActivity extends AppCompatActivity {

    private LinearLayout reservationsLayout;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);

        reservationsLayout = findViewById(R.id.reservationsLayout);
        dbHelper = new DatabaseHelper(this);

        displayReservations();
    }

    private void displayReservations() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_RESERVATIONS, null);

        if (cursor.moveToFirst()) {
            do {
                String origin = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORIGIN));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESTINATION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE));

                TextView reservationInfo = new TextView(this);
                reservationInfo.setText("Origin: " + origin + "\nDestination: " + destination + "\nDate: " + date + "\nPrice: $" + price + "\n\n");
                reservationsLayout.addView(reservationInfo);

            } while (cursor.moveToNext());
        } else {
            TextView noReservations = new TextView(this);
            noReservations.setText("No Reservations Found");
            reservationsLayout.addView(noReservations);
        }
        cursor.close();
        db.close();
    }
}