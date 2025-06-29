package com.example.lab8_q3;
import android.provider.BaseColumns;

public final class ClinicContract {

    private ClinicContract() {
    }

    public static final class AppointmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "appointment_table";
        public static final String COLUMN_PATIENT_NAME = "patient_name";
        public static final String COLUMN_DOCTOR_NAME = "doctor_name";
        public static final String COLUMN_APPOINTMENT_DATE = "appointment_date";
    }
}