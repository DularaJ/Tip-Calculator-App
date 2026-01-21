package com.example.tipcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextBill;
    SeekBar seekBarTip, seekBarSplit;
    TextView textTipLabel,textSplitLabel;
    RadioGroup radioGroupService, radioGroupRounding;
    Button btnCalculate;

    double billAmount = 0;
    int tipPercent = 15;
    int numPeople = 1;
    boolean roundUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBill = findViewById(R.id.editTextBill);
        seekBarTip=findViewById(R.id.seekBarTip);
        textTipLabel = findViewById(R.id.textTipLabel);
        seekBarSplit = findViewById(R.id.seekBarSplit);
        textSplitLabel = findViewById(R.id.textSplitLabel);
        radioGroupService = findViewById(R.id.radioGroupService);
        radioGroupRounding = findViewById(R.id.radioGroupRounding);
        btnCalculate = findViewById(R.id.btnCalculate);

        seekBarTip.setProgress(15);
        seekBarSplit.setProgress(1);

        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercent = progress;
                textTipLabel.setText("Tip Percentage: " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSplit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numPeople = Math.max(progress, 1);
                textSplitLabel.setText("Number of People: " + numPeople);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radioGroupService.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbPoor) seekBarTip.setProgress(5);
            else if (checkedId == R.id.rbGood) seekBarTip.setProgress(10);
            else if (checkedId == R.id.rbExcellent) seekBarTip.setProgress(20);
        });

        btnCalculate.setOnClickListener(v -> {
            String billStr = editTextBill.getText().toString().trim();
            if (billStr.isEmpty()){
                Toast.makeText(this,"Enter bill amount",Toast.LENGTH_SHORT).show();
                return;
            }

            billAmount = Double.parseDouble(billStr);

            int roundingId = radioGroupRounding.getCheckedRadioButtonId();
            roundUp = (roundingId==R.id.rbRoundUp);

            Intent intent = new Intent(MainActivity.this, ReceiptActivity.class);
            intent.putExtra("billAmount",billAmount);
            intent.putExtra("tipPercent",tipPercent);
            intent.putExtra("numPeople",numPeople);
            intent.putExtra("roundUp",roundUp);
            startActivity(intent);
        });

    }

    private void setTip(int percent){
        tipPercent = percent;
        seekBarTip.setProgress(percent);
        textTipLabel.setText("Tip Percentage: "+ percent+"%");
    }

}