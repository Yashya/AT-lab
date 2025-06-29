package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultText = findViewById(R.id.resultText);

        Intent intent = getIntent();

        // Safeguard: Check if intent has extra data
        int score = intent.getIntExtra("SCORE", -1);
        int total = intent.getIntExtra("TOTAL", -1);

        if (score == -1 || total == -1) {
            resultText.setText("Error loading results!"); // Prevent crashes
        } else {
            resultText.setText("You scored " + score + " out of " + total);
        }
    }
}
