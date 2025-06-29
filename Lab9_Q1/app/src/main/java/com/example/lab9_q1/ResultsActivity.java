package com.example.lab9_q1;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView resultsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsText = findViewById(R.id.resultsText);
        dbHelper = new DatabaseHelper(this);

        displayResults();
    }

    private void displayResults() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SURVEY, null);

        StringBuilder results = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                String answer = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ANSWER));
                String comments = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COMMENTS));
                results.append("Answer: ").append(answer).append("\nComments: ").append(comments).append("\n\n");
            } while (cursor.moveToNext());
        } else {
            results.append("No results found.");
        }
        cursor.close();
        db.close();

        resultsText.setText(results.toString());
    }
}