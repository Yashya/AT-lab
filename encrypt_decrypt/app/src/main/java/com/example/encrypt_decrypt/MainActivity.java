package com.example.encrypt_decrypt;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private EditText etInput;
    private Button btnEncrypt, btnDecrypt;
    private TextView tvEncryptedText, tvDecryptedText;
    private SecretKey secretKey;
    private String encryptedText = ""; // Stores encrypted text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        btnEncrypt = findViewById(R.id.btnEncrypt);
        btnDecrypt = findViewById(R.id.btnDecrypt);
        tvEncryptedText = findViewById(R.id.tvEncryptedText);
        tvDecryptedText = findViewById(R.id.tvDecryptedText);

        // Generate AES Secret Key
        secretKey = generateKey();

        btnEncrypt.setOnClickListener(view -> {
            String inputText = etInput.getText().toString();
            if (!inputText.isEmpty()) {
                encryptedText = encrypt(inputText, secretKey);
                tvEncryptedText.setText("Encrypted Output:\n" + encryptedText);
                tvDecryptedText.setText(""); // Clear decrypted text when new encryption is done
            } else {
                tvEncryptedText.setText("Enter text to encrypt!");
            }
        });

        btnDecrypt.setOnClickListener(view -> {
            if (!encryptedText.isEmpty()) {
                String decryptedText = decrypt(encryptedText, secretKey);
                tvDecryptedText.setText("Decrypted Output:\n" + decryptedText);
            } else {
                tvDecryptedText.setText("No encrypted text to decrypt!");
            }
        });
    }

    private SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, new SecureRandom()); // AES-256
            return keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String encrypt(String data, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return "Encryption Error!";
        }
    }

    private String decrypt(String encryptedData, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "Decryption Error!";
        }
    }
}
