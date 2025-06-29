package com.example.lab9_q1;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SurveyActivity extends AppCompatActivity {

    private RadioGroup radioGroup1;
    private EditText editTextComments;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        radioGroup1 = findViewById(R.id.radioGroup1);
        editTextComments = findViewById(R.id.editTextComments);
        Button submitButton = findViewById(R.id.submitButton);
        dbHelper = new DatabaseHelper(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup1.getCheckedRadioButtonId();
                String comments = editTextComments.getText().toString();

                if (selectedId == -1) {
                    Toast.makeText(SurveyActivity.this, "Please answer the question", Toast.LENGTH_SHORT).show();
                    return;
                }

                String answer = "";
                if (selectedId == R.id.radio1_1) {
                    answer = "Very Satisfied";
                } else if (selectedId == R.id.radio1_2) {
                    answer = "Satisfied";
                } else if (selectedId == R.id.radio1_3) {
                    answer = "Neutral";
                } else if (selectedId == R.id.radio1_4) {
                    answer = "Dissatisfied";
                } else if (selectedId == R.id.radio1_5) {
                    answer = "Very Dissatisfied";
                }

                saveData(answer, comments);
            }
        });
    }

    private void saveData(String answer, String comments) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ANSWER, answer);
        values.put(DatabaseHelper.COLUMN_COMMENTS, comments);
        long newRowId = db.insert(DatabaseHelper.TABLE_SURVEY, null, values);
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Survey submitted", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error submitting survey", Toast.LENGTH_SHORT).show();
        }
    }
}