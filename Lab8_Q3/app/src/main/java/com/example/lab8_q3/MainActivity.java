package com.example.lab8_q3;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText patientNameEditText, doctorNameEditText, appointmentDateEditText;
    private Button bookAppointmentButton;
    private TextView appointmentResultTextView;
    private ClinicDBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientNameEditText = findViewById(R.id.patientNameEditText);
        doctorNameEditText = findViewById(R.id.doctorNameEditText);
        appointmentDateEditText = findViewById(R.id.appointmentDateEditText);
        bookAppointmentButton = findViewById(R.id.bookAppointmentButton);
        appointmentResultTextView = findViewById(R.id.appointmentResultTextView);

        dbHelper = new ClinicDBHelper(this);
        db = dbHelper.getWritableDatabase();

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment();
            }
        });
    }

    private void bookAppointment() {
        String patientName = patientNameEditText.getText().toString();
        String doctorName = doctorNameEditText.getText().toString();
        String appointmentDate = appointmentDateEditText.getText().toString();

        if (patientName.isEmpty() || doctorName.isEmpty() || appointmentDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isDoctorAvailable(doctorName, appointmentDate)) {
            ContentValues values = new ContentValues();
            values.put(ClinicContract.AppointmentEntry.COLUMN_PATIENT_NAME, patientName);
            values.put(ClinicContract.AppointmentEntry.COLUMN_DOCTOR_NAME, doctorName);
            values.put(ClinicContract.AppointmentEntry.COLUMN_APPOINTMENT_DATE, appointmentDate);

            long newRowId = db.insert(ClinicContract.AppointmentEntry.TABLE_NAME, null, values);

            if (newRowId != -1) {
                appointmentResultTextView.setText("Appointment booked successfully.");
                clearFields();
            } else {
                appointmentResultTextView.setText("Error booking appointment.");
            }
        } else {
            appointmentResultTextView.setText("Doctor not available on that date.");
        }
    }

    private boolean isDoctorAvailable(String doctorName, String appointmentDate) {
        String[] projection = {ClinicContract.AppointmentEntry._ID};
        String selection = ClinicContract.AppointmentEntry.COLUMN_DOCTOR_NAME + " = ? AND " + ClinicContract.AppointmentEntry.COLUMN_APPOINTMENT_DATE + " = ?";
        String[] selectionArgs = {doctorName, appointmentDate};

        Cursor cursor = db.query(
                ClinicContract.AppointmentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean available = cursor.getCount() == 0;
        cursor.close();
        return available;
    }

    private void clearFields() {
        patientNameEditText.getText().clear();
        doctorNameEditText.getText().clear();
        appointmentDateEditText.getText().clear();
    }
}