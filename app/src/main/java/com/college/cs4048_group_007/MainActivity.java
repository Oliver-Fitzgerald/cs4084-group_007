package com.college.cs4048_group_007;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.college.cs4048_group_007.popup.DescriptionPopupComponent;
import com.college.cs4048_group_007.popup.Popup;
import com.college.cs4048_group_007.popup.WaitInfoPopupComponent;
import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Field;
import java.util.Map;

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
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

    }
}