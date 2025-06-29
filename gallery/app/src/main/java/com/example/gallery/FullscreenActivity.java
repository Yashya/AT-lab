package com.example.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FullscreenActivity extends AppCompatActivity {

    private ImageView fullscreenImageView;
    private ImageButton btnPrev, btnNext;
    private int position;
    private int[] images = {
            R.drawable.image1, R.drawable.image2, R.drawable.image3,
            R.drawable.image4, R.drawable.image5, R.drawable.image6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        fullscreenImageView = findViewById(R.id.fullscreenImageView);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        int imageId = intent.getIntExtra("image_id", -1);

        if (imageId == -1) {
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            finish(); // Closes activity if no image is received
            return;
        }

        fullscreenImageView.setImageResource(imageId);

        btnPrev.setOnClickListener(view -> {
            if (position > 0) {
                position--;
                fullscreenImageView.setImageResource(images[position]);
            }
        });

        btnNext.setOnClickListener(view -> {
            if (position < images.length - 1) {
                position++;
                fullscreenImageView.setImageResource(images[position]);
            }
        });
    }
}
