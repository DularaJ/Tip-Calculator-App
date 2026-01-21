package com.example.tipcalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReceiptActivity extends AppCompatActivity {

    TextView textReceipt;
    Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        textReceipt = findViewById(R.id.textReceipt);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        double billAmount = getIntent().getDoubleExtra("billAmount", 0);
        int tipPercent = getIntent().getIntExtra("tipPercent", 15);
        int numPeople = getIntent().getIntExtra("numPeople", 1);
        boolean roundUp = getIntent().getBooleanExtra("roundUp", false);

        // Calculations
        double tip = billAmount * tipPercent / 100.0;
        double total = billAmount + tip;
        double perPerson = total / numPeople;

        if (roundUp) {
            total = Math.ceil(total);
            perPerson = Math.ceil(perPerson);
        }

        // Instead of StringBuilder + append, use one formatted string
        String receiptText =
                "Bill Amount: " + billAmount + "\n" +
                        "Tip: " + tipPercent + "% = " + String.format("%.2f", tip) + "\n" +
                        "Total: " + String.format("%.2f", total) + "\n" +
                        "Split (" + numPeople + " people): " + String.format("%.2f", perPerson) + "\n" +
                        "Rounding: " + (roundUp ? "Applied" : "Not Applied");

        textReceipt.setText(receiptText);

        btnSave.setOnClickListener(v ->
                Toast.makeText(this, "Saved to history", Toast.LENGTH_SHORT).show()
        );

        btnBack.setOnClickListener(v -> finish());
    }
}
