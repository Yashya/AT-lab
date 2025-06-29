package com.example.lab9_q2;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FlightResultsActivity extends AppCompatActivity {

    private LinearLayout resultsLayout;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_results);

        resultsLayout = findViewById(R.id.resultsLayout);
        dbHelper = new DatabaseHelper(this);

        String origin = getIntent().getStringExtra("origin");
        String destination = getIntent().getStringExtra("destination");
        String date = getIntent().getStringExtra("date");

        displayFlightResults(origin, destination, date);
    }

    private void displayFlightResults(String origin, String destination, String date) {
        //Simulated Flight Results, replace with actual flight search logic.
        for(int i = 0; i<3; i++){
            TextView flightInfo = new TextView(this);
            flightInfo.setText("Flight "+(i+1)+": "+ origin + " to " + destination + " on " + date + " Price: $"+(100+i*50));
            resultsLayout.addView(flightInfo);

            Button reserveButton = new Button(this);
            reserveButton.setText("Reserve");

            // Create final copies of the variables for each button.
            final String finalOrigin = origin;
            final String finalDestination = destination;
            final String finalDate = date;
            final int finalPrice = 100 + i * 50;

            reserveButton.setOnClickListener(v -> reserveFlight(finalOrigin, finalDestination, finalDate, finalPrice));
            resultsLayout.addView(reserveButton);
        }
    }

    private void reserveFlight(String origin, String destination, String date, int price){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ORIGIN, origin);
        values.put(DatabaseHelper.COLUMN_DESTINATION, destination);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_PRICE, price);
        long newRowId = db.insert(DatabaseHelper.TABLE_RESERVATIONS, null, values);
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Reservation Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Reservation Failed", Toast.LENGTH_SHORT).show();
        }
    }
}