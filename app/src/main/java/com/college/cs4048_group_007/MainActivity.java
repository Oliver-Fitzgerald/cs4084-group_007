package com.college.cs4048_group_007;

import android.content.Context;
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

        Button myButton = findViewById(R.id.myButton);

        //This is how you would add a popup to that button
        //Assuming that we get data from db like
        int lineLength = 32;
        String description = "This great ride lets you ride the ride!";

        //We can format the data like so.
        //Maps might seem complicated but it helps if we have components
        //with multiple inputs
        Map<String, Object> wait_component_data = Map.of(
                "lineLength", lineLength
        );

        Map<String, Object> description_component_data = Map.of(
                "description", description
        );

        //Lastly we can combine these into the final object for the popup's update method
        Map<String, Map<String, Object>> data = Map.of(
                "wait-info", wait_component_data,
                "description", description_component_data
        );

        Context context = this.getBaseContext();

        //This is how we build the popup.
        //We set the base layout of the component
        //And then simply add components as needed
        Popup popup = new Popup.Builder()
                .init(context)
                .setBase(R.layout.rollercoaster_base)
                .addComponent(new DescriptionPopupComponent(context))
                .addComponent(new WaitInfoPopupComponent(context))
                .build();

        popup.update(data);

        myButton.setOnClickListener(v -> {
            LinearLayout popupContainer = findViewById(R.id.popup_container);

            int[] location = new int[2];
            myButton.getLocationInWindow(location);

            popupContainer.setX(location[0] - 350);
            popupContainer.setY(location[1] + myButton.getHeight());

            View popupView = popup.getView();

            if(popupView.getParent() != null)
                ((ViewGroup) popupView.getParent()).removeView(popupView);

            popupContainer.addView(popupView);

            popupView.setVisibility(popupView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

    }
}