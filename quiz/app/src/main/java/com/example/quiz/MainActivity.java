package com.example.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button nextButton;
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        nextButton = findViewById(R.id.nextButton);

        // Load questions
        questions = getSampleQuestions();
        loadQuestion();

        nextButton.setOnClickListener(v -> {
            if (optionsGroup.getCheckedRadioButtonId() == -1) {
                showAlert("Please select an option!");
                return;
            }

            checkAnswer();
            currentQuestionIndex++;

            if (currentQuestionIndex < questions.size()) {
                loadQuestion();
            } else {
                showConfirmationDialog();
            }
        });
    }

    private void loadQuestion() {
        optionsGroup.clearCheck();
        Question question = questions.get(currentQuestionIndex);
        questionText.setText(question.getQuestion());

        ((RadioButton) optionsGroup.getChildAt(0)).setText(question.getOptions()[0]);
        ((RadioButton) optionsGroup.getChildAt(1)).setText(question.getOptions()[1]);
        ((RadioButton) optionsGroup.getChildAt(2)).setText(question.getOptions()[2]);
        ((RadioButton) optionsGroup.getChildAt(3)).setText(question.getOptions()[3]);
    }

    private void checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedAnswer = selectedRadioButton.getText().toString();

        if (selectedAnswer.equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
            score++;
        }
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Submit Quiz")
                .setMessage("Are you sure you want to submit the quiz?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("SCORE", score);
                    intent.putExtra("TOTAL", questions.size()); // Ensure TOTAL is passed
                    startActivity(intent);
                    finish(); // Finish MainActivity
                })
                .setNegativeButton("No", null)
                .show();
    }

    private ArrayList<Question> getSampleQuestions() {
        ArrayList<Question> sampleQuestions = new ArrayList<>();
        sampleQuestions.add(new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, "Paris"));
        sampleQuestions.add(new Question("Who developed Java?", new String[]{"Microsoft", "Sun Microsystems", "Apple", "Google"}, "Sun Microsystems"));
        sampleQuestions.add(new Question("What is 5 + 3?", new String[]{"6", "7", "8", "9"}, "8"));
        return sampleQuestions;
    }
}
