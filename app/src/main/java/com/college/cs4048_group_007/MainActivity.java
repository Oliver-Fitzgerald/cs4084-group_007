package com.college.cs4048_group_007;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    final private String RESET = "\u001B[0m";
    final private String RED = "\u001B[41m";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Get Team Names
        String teamMemberNames = "";
        Field[] allStringFields = R.string.class.getDeclaredFields();
        for (Field field: allStringFields) {
           if (field.getName().startsWith("team_member")) {

               try {
                   String teamMemberName = this.getApplication().getString(field.getInt(null));
                   teamMemberNames += teamMemberName + "\n";
               } catch (IllegalAccessException exception){
                   System.out.printf("%sError%s: getting the string values from values/strings.xml\nMessage: %s\n",RED,RESET,exception.getMessage());
               }
           }
        }

        //Set Main Activity to display team names
        TextView centerTextView = findViewById(R.id.centerTextView) ;
        centerTextView.setText(teamMemberNames);

        MaterialButton myButton = findViewById(R.id.myButton);

        myButton.setOnClickListener(v -> {
            //Displays loading screen until the activity passed as parameter is ready
            /*
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
             */
            Intent intent = new Intent(this, LoadingMap.class);
            intent.putExtra("load","bitmaps");
            startActivity(intent);
        });

        Button shopButton = findViewById(R.id.btnOpenShop);
        shopButton.setOnClickListener(v -> {
            System.out.println("Shop button clicked`");
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_help) {
            showHelpSheet();
            return true;
        }
        if (item.getItemId() == R.id.menu_stats) {
            showDailyStats();
            return true;
        }
        if (item.getItemId() == R.id.menu_toggle_theme) {
            toggleDarkMode();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = LayoutInflater.from(this)
                .inflate(R.layout.help_sheet_layout, null);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    private void showDailyStats() {
        int visitedCount = VisitedTracker.getVisitedCount(this);
        BottomSheetDialog sheet = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.daily_stats_layout, null);
        TextView statText = view.findViewById(R.id.stat_text);
        statText.setText("You've visited " + visitedCount + " attractions today!");
        sheet.setContentView(view);
        sheet.show();
    }


    private void toggleDarkMode() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();

        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        recreate(); // Optional: refresh the activity immediately
    }
}