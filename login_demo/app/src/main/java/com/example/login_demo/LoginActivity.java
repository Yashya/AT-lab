package com.example.login_demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Login")
                .setMessage("Are you sure you want to log in?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        validateCredentials();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void validateCredentials() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hardcoded credentials for testing
        if (username.equals("user1") && password.equals("password1")) {
            openProfileActivity("User 1", "Welcome to your profile, User 1!");
        } else if (username.equals("user2") && password.equals("password2")) {
            openProfileActivity("User 2", "Welcome to your profile, User 2!");
        } else {
            Toast.makeText(this, "Invalid credentials, please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openProfileActivity(String username, String message) {
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("MESSAGE", message);
        startActivity(intent);
    }
}
