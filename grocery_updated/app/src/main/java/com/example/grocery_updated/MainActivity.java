package com.example.grocery_updated;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LinearLayout itemsContainer;
    private Button btnShowBill;
    private TextView tvCart, tvBill;

    private HashMap<String, Integer> cart = new HashMap<>();
    private HashMap<String, Integer> priceList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        itemsContainer = findViewById(R.id.itemsContainer);
        btnShowBill = findViewById(R.id.btnShowBill);
        tvCart = findViewById(R.id.tvCart);
        tvBill = findViewById(R.id.tvBill);

        // Grocery items with prices
        String[] items = {"Rice", "Sugar", "Milk", "Eggs"};
        int[] prices = {50, 40, 60, 6};

        for (int i = 0; i < items.length; i++) {
            priceList.put(items[i], prices[i]);
            addItemToUI(items[i], prices[i]);
        }

        // Show Bill Button
        btnShowBill.setOnClickListener(view -> generateBill());
    }

    private void addItemToUI(String itemName, int price) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(10, 10, 10, 10);

        TextView tvItem = new TextView(this);
        tvItem.setText(itemName + " - ₹" + price);
        tvItem.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        Button btnMinus = new Button(this);
        btnMinus.setText("−");
        btnMinus.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

        TextView tvQuantity = new TextView(this);
        tvQuantity.setText("0");
        tvQuantity.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvQuantity.setGravity(View.TEXT_ALIGNMENT_CENTER);

        Button btnPlus = new Button(this);
        btnPlus.setText("+");
        btnPlus.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

        // Decrease quantity
        btnMinus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            if (quantity > 0) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                if (quantity == 0) {
                    cart.remove(itemName);
                } else {
                    cart.put(itemName, quantity);
                }
                updateCart();
            }
        });

        // Increase quantity
        btnPlus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            cart.put(itemName, quantity);
            updateCart();
        });

        itemLayout.addView(tvItem);
        itemLayout.addView(btnMinus);
        itemLayout.addView(tvQuantity);
        itemLayout.addView(btnPlus);

        itemsContainer.addView(itemLayout);
    }

    private void updateCart() {
        StringBuilder cartText = new StringBuilder("Cart:\n");
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            cartText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        tvCart.setText(cartText.toString());
    }

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
