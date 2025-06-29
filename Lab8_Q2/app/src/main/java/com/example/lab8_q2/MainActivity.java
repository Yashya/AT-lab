package com.example.lab8_q2;
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

    private EditText productNameEditText, quantityEditText, priceEditText;
    private Button addButton, generateInvoiceButton, productInfoButton;
    private TextView invoiceTextView;
    private InvoiceDBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productNameEditText = findViewById(R.id.productNameEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        priceEditText = findViewById(R.id.priceEditText);
        addButton = findViewById(R.id.addButton);
        generateInvoiceButton = findViewById(R.id.generateInvoiceButton);
        invoiceTextView = findViewById(R.id.invoiceTextView);
        productInfoButton = findViewById(R.id.productInfoButton);

        dbHelper = new InvoiceDBHelper(this);
        db = dbHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        generateInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateInvoice();
            }
        });

        productInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductInfo();
            }
        });
    }

    private void addItem() {
        String productName = productNameEditText.getText().toString();
        String quantityStr = quantityEditText.getText().toString();
        String priceStr = priceEditText.getText().toString();

        if (productName.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);

        ContentValues values = new ContentValues();
        values.put(InvoiceContract.InvoiceEntry.COLUMN_PRODUCT_NAME, productName);
        values.put(InvoiceContract.InvoiceEntry.COLUMN_QUANTITY, quantity);
        values.put(InvoiceContract.InvoiceEntry.COLUMN_PRICE, price);

        long newRowId = db.insert(InvoiceContract.InvoiceEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
            productNameEditText.getText().clear();
            quantityEditText.getText().clear();
            priceEditText.getText().clear();
        } else {
            Toast.makeText(this, "Error adding item", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateInvoice() {
        Cursor cursor = db.query(
                InvoiceContract.InvoiceEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            invoiceTextView.setText("No items to generate invoice");
            return;
        }

        StringBuilder invoice = new StringBuilder("Invoice:\n\n");
        double totalAmount = 0;

        while (cursor.moveToNext()) {
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(InvoiceContract.InvoiceEntry.COLUMN_PRODUCT_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InvoiceContract.InvoiceEntry.COLUMN_QUANTITY));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(InvoiceContract.InvoiceEntry.COLUMN_PRICE));

            double itemTotal = quantity * price;
            invoice.append(productName).append(" x ").append(quantity).append(" = $").append(String.format("%.2f", itemTotal)).append("\n");
            totalAmount += itemTotal;
        }

        invoice.append("\nTotal Amount: $").append(String.format("%.2f", totalAmount));
        invoiceTextView.setText(invoice.toString());
        cursor.close();
    }

    private void getProductInfo() {
        String sql = "SELECT COUNT(*) as total_products, " +
                "SUM(" + InvoiceContract.InvoiceEntry.COLUMN_PRICE + " * " + InvoiceContract.InvoiceEntry.COLUMN_QUANTITY + ") as total_price, " +
                "MAX(" + InvoiceContract.InvoiceEntry.COLUMN_PRICE + ") as max_price, " +
                "MIN(" + InvoiceContract.InvoiceEntry.COLUMN_PRICE + ") as min_price, " +
                "(SELECT " + InvoiceContract.InvoiceEntry.COLUMN_PRODUCT_NAME + " FROM " + InvoiceContract.InvoiceEntry.TABLE_NAME + " ORDER BY " + InvoiceContract.InvoiceEntry.COLUMN_PRICE + " DESC LIMIT 1) as max_product_name, " +
                "(SELECT " + InvoiceContract.InvoiceEntry.COLUMN_PRODUCT_NAME + " FROM " + InvoiceContract.InvoiceEntry.TABLE_NAME + " ORDER BY " + InvoiceContract.InvoiceEntry.COLUMN_PRICE + " ASC LIMIT 1) as min_product_name " +
                "FROM " + InvoiceContract.InvoiceEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            int totalProducts = cursor.getInt(cursor.getColumnIndexOrThrow("total_products"));
            double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
            double maxPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("max_price"));
            double minPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("min_price"));
            String maxProductName = cursor.getString(cursor.getColumnIndexOrThrow("max_product_name"));
            String minProductName = cursor.getString(cursor.getColumnIndexOrThrow("min_product_name"));

            String info = "Total Products: " + totalProducts + "\n" +
                    "Total Price: $" + String.format("%.2f", totalPrice) + "\n" +
                    "Maximum Price: $" + String.format("%.2f", maxPrice) + " (" + maxProductName + ")\n" +
                    "Minimum Price: $" + String.format("%.2f", minPrice) + " (" + minProductName + ")";

            invoiceTextView.setText(info);
        } else {
            invoiceTextView.setText("No product information available.");
        }

        cursor.close();
    }
}