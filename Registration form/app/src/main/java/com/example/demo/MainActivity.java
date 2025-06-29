package com.example.demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText, mobileEditText, passwordEditText;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> validateInput());
    }

    private void validateInput() {
        String email = emailEditText.getText().toString().trim();
        String mobile = mobileEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Invalid Email Address!");
            return;
        }

        if (mobile.length() != 10 || !TextUtils.isDigitsOnly(mobile)) {
            showToast("Invalid Mobile Number!");
            return;
        }

        if (password.isEmpty()) {
            showToast("Password cannot be empty!");
            return;
        }

        showToast("Registration Successful!");
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
