package com.example.grocery;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerItems;
    private EditText etQuantity;
    private Button btnAddItem, btnShowBill;
    private TextView tvBill, tvCart;

    private HashMap<String, Integer> cart = new HashMap<>();
    private HashMap<String, Integer> priceList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        spinnerItems = findViewById(R.id.spinnerItems);
        etQuantity = findViewById(R.id.etQuantity);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnShowBill = findViewById(R.id.btnShowBill);
        tvBill = findViewById(R.id.tvBill);
        tvCart = findViewById(R.id.tvCart);

        // Grocery items with prices
        String[] items = {"Rice - ₹50/kg", "Sugar - ₹40/kg", "Milk - ₹60/ltr", "Eggs - ₹6/each"};
        priceList.put("Rice", 50);
        priceList.put("Sugar", 40);
        priceList.put("Milk", 60);
        priceList.put("Eggs", 6);

        // Set Spinner Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(adapter);

        // Button Click Listener to Add Items
        btnAddItem.setOnClickListener(view -> {
            String selectedItem = spinnerItems.getSelectedItem().toString().split(" - ")[0]; // Extract Item Name
            String quantityStr = etQuantity.getText().toString();

            if (quantityStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter quantity!", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            cart.put(selectedItem, cart.getOrDefault(selectedItem, 0) + quantity);

            updateCart();
            etQuantity.setText("");
            Toast.makeText(MainActivity.this, selectedItem + " added!", Toast.LENGTH_SHORT).show();
        });

        // Button Click Listener to Generate Bill
        btnShowBill.setOnClickListener(view -> generateBill());
    }

    // Update Cart Display
    private void updateCart() {
        StringBuilder cartText = new StringBuilder("Cart:\n");
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            cartText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        tvCart.setText(cartText.toString());
    }

    // Generate Bill
    private void generateBill() {
        StringBuilder billText = new StringBuilder("Bill Details:\n");
        int totalAmount = 0, totalItems = 0;

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String item = entry.getKey();
            int quantity = entry.getValue();
            int price = priceList.get(item) * quantity;

            billText.append(item).append(": ").append(quantity).append(" x ₹").append(priceList.get(item)).append(" = ₹").append(price).append("\n");
            totalAmount += price;
            totalItems += quantity;
        }

        billText.append("\nTotal Items: ").append(totalItems);
        billText.append("\nTotal Amount: ₹").append(totalAmount);

        tvBill.setText(billText.toString());
    }
}
