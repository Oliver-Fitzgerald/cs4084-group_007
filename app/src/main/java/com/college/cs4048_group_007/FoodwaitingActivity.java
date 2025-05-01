package com.college.cs4048_group_007;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FoodwaitingActivity extends AppCompatActivity {
    final static private String RESET = "\u001B[0m";
    final static private String RED = "\u001B[41m";

    private TextView timerText;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_wait);
        Log.d("FoodwaitingActivity", "Activity loaded");

        timerText = findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(10_000, 1_000) {
            @Override
            public void onTick(long millisUntilFinished) {

                // to do: insert into transaction table (status = "waiting")
                int seconds = (int) millisUntilFinished / 1000;
                String formatted = String.format("00 : 00 : %02d", seconds);
                timerText.setText(formatted);
            }

            @Override
            public void onFinish() {
                timerText.setText("00 : 00 : 00");
                Toast.makeText(FoodwaitingActivity.this, "Your food is ready!", Toast.LENGTH_SHORT).show();
                // to do: insert into transaction table (status = "done")

            }
        };

        countDownTimer.start();
        Button backButton = findViewById(R.id.back);
        if (backButton != null) {
            Log.d("DEBUG", "Back button found");
            backButton.setOnClickListener(v -> {
                Log.d("DEBUG", "Back button clicked");
                finish();
            });
        } else {
            Log.e("DEBUG", "Back button is null — ID or layout mismatch?");
        }
    }


}
