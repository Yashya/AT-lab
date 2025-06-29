package com.example.change_back;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View mainLayout; // Reference to the background layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the layout reference
        mainLayout = findViewById(R.id.mainLayout);

        // Get buttons
        Button btnRed = findViewById(R.id.btnRed);
        Button btnGreen = findViewById(R.id.btnGreen);
        Button btnBlue = findViewById(R.id.btnBlue);

        // Set click listeners for color change
        btnRed.setOnClickListener(v -> changeBackgroundColor(Color.RED));
        btnGreen.setOnClickListener(v -> changeBackgroundColor(Color.GREEN));
        btnBlue.setOnClickListener(v -> changeBackgroundColor(Color.BLUE));
    }

    private void changeBackgroundColor(int color) {
        mainLayout.setBackgroundColor(color);
    }
}
