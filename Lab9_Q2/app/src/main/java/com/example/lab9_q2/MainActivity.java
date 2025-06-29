package com.example.lab9_q2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextOrigin, editTextDestination, editTextDate;
    private Button buttonSearch, buttonViewReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextOrigin = findViewById(R.id.editTextOrigin);
        editTextDestination = findViewById(R.id.editTextDestination);
        editTextDate = findViewById(R.id.editTextDate);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonViewReservations = findViewById(R.id.buttonViewReservations);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlightResultsActivity.class);
                intent.putExtra("origin", editTextOrigin.getText().toString());
                intent.putExtra("destination", editTextDestination.getText().toString());
                intent.putExtra("date", editTextDate.getText().toString());
                startActivity(intent);
            }
        });

        buttonViewReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReservationDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}