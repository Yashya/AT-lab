package com.example.lab7_q1;
import android.provider.BaseColumns;

public final class StudentContract {

    private StudentContract() {
    }

    public static final class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME = "student_table";
        public static final String COLUMN_ROLL_NUMBER = "roll_number";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MARKS = "marks";
    }
}