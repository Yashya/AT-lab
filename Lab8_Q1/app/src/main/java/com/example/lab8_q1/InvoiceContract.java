package com.example.lab8_q1;
import android.provider.BaseColumns;

public final class InvoiceContract {

    private InvoiceContract() {
    }

    public static final class InvoiceEntry implements BaseColumns {
        public static final String TABLE_NAME = "invoice_table";
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
    }
}
