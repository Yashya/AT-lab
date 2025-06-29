package com.example.login_demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewDetails = findViewById(R.id.textViewDetails);

        // Get data from Intent
        String username = getIntent().getStringExtra("USERNAME");
        String message = getIntent().getStringExtra("MESSAGE");

        textViewWelcome.setText("Welcome, " + username + "!");
        textViewDetails.setText(message);
    }
}
