package com.college.cs4048_group_007;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

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

        String attractionName = getIntent().getStringExtra("attraction_name");
        long timer = getIntent().getLongExtra("timer", 10000);
        String foodKey = "food_" + attractionName;

        timerText = findViewById(R.id.timer);
        SharedPreferences prefs = getSharedPreferences("food_timer", MODE_PRIVATE);
        long startTime = prefs.getLong("start_time" + foodKey, 0);
        long duration = prefs.getLong("duration" + foodKey, 0);

        if (startTime == 0 || duration == 0) {

            long newDuration = timer;


            long newStartTime = System.currentTimeMillis();

            prefs.edit()
                    .putLong("start_time", newStartTime)
                    .putLong("duration", newDuration)
                    .apply();

            startTimer(newDuration);
            Log.d("DEBUG", "Started new timer");
        } else {
            // Timer existed — check if it's still running
            long now = System.currentTimeMillis();
            long elapsed = now - startTime;
            long remaining = duration - elapsed;

            if (remaining > 0) {
                startTimer(remaining);
                Log.d("DEBUG", "Resuming timer: " + remaining);
            } else {
                timerText.setText("00 : 00 : 00");
                Toast.makeText(getApplicationContext(), "Your food has already ready!", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", "Timer expired — not restarting.");
            }
        }

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

    private void startTimer(long timeInMillis) {
        // insert to database (status = "preparing")


        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                String formatted = String.format("00 : 00 : %02d", seconds);
                timerText.setText(formatted);
            }

            public void onFinish() {
                timerText.setText("00 : 00 : 00");
                Toast.makeText(getApplicationContext(), "Your food is ready!", Toast.LENGTH_SHORT).show();
                // insert to database (status = "done")

            }
        };
        countDownTimer.start();
    }
}