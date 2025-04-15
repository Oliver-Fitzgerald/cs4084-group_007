package com.college.cs4048_group_007;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.college.cs4048_group_007.data.AppDatabase;
import com.college.cs4048_group_007.data.PoiDao;
import com.college.cs4048_group_007.data.PoiRepository;
import com.college.cs4048_group_007.data.RideDao;
import com.college.cs4048_group_007.data.RideRepository;
import com.college.cs4048_group_007.entities.Poi;
import com.college.cs4048_group_007.entities.Ride;
import com.college.cs4048_group_007.pathing.POI;
import com.college.cs4048_group_007.popup.ButtonPopupComponent;
import com.college.cs4048_group_007.popup.DescriptionPopupComponent;
import com.college.cs4048_group_007.popup.Popup;
import com.college.cs4048_group_007.popup.WaitInfoPopupComponent;
import com.college.cs4048_group_007.viewmodel.PoiViewModel;
import com.college.cs4048_group_007.viewmodel.PoiViewModelFactory;
import com.college.cs4048_group_007.viewmodel.RideViewModel;
import com.college.cs4048_group_007.viewmodel.RideViewModelFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

public class MapActivity extends AppCompatActivity {
    final private String RESET = "\u001B[0m";
    final private String RED = "\u001B[41m";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.map_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.map), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Context context = this.getBaseContext();

        //This is how we build the popup.
        //We set the base layout of the component
        //And then simply add components as needed
        Popup popup = new Popup.Builder()
                .init(context)
                .setBase(R.layout.rollercoaster_base)
                .addComponent(new DescriptionPopupComponent(context))
                .addComponent(new ButtonPopupComponent(context))
                .build();

        //This is how you would add a popup to that button
        POI  merryGoRound = findViewById(R.id.merry_go_round) ;
        merryGoRound.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this.getApplicationContext(),"you clicked me well done", Toast.LENGTH_SHORT);
            toast.show();
        });



        PoiRepository poiRepository = new PoiRepository(getApplication());
        PoiViewModelFactory factory = new PoiViewModelFactory(poiRepository);
        PoiViewModel poiViewModel = new ViewModelProvider(this, factory).get(PoiViewModel.class);

        RideRepository rideRepository = new RideRepository(getApplication());
        RideViewModelFactory factoryRide = new RideViewModelFactory(rideRepository);
        RideViewModel rideViewModel = new ViewModelProvider(this, factoryRide).get(RideViewModel.class);

        poiViewModel.getRidePoiById(1).observe(this, ridePoi -> {

            if (ridePoi != null) {
                // Log the description to confirm data retrieval
                android.util.Log.d("MapActivity3", "Description fetched: " + ridePoi.name);
                // Show a Toast for quick feedback
                Toast.makeText(this, "Fetched description3: " + ridePoi.name, Toast.LENGTH_SHORT).show();

                String description = "Name: " + ridePoi.name + "\n" +
                        "Description: " + ridePoi.description + "\n" +
                        "Open & Close time: " + ridePoi.openTime + "am--" + ridePoi.closeTime + "pm\n" +
                        "Rating: " + ridePoi.rating;

                // Update the popup data dynamically
                Map<String, Object> descriptionComponentData = Map.of("description", description);

                Map<String, Map<String, Object>> data = Map.of(
                        "description", descriptionComponentData
                );
                popup.update(data);
            }
        });



        merryGoRound.setOnClickListener(v -> {

            Toast.makeText(context, "You clicked the merry go round", Toast.LENGTH_SHORT).show();
            LinearLayout popupContainer = findViewById(R.id.popup_container);

            int[] location = new int[2];
            merryGoRound.getLocationInWindow(location);

            popupContainer.setX(location[0] - 200);
            popupContainer.setY(location[1] + merryGoRound.getHeight() - 150);

            View popupView = popup.getView();

            if(popupView.getParent() != null)
                ((ViewGroup) popupView.getParent()).removeView(popupView);

            popupContainer.addView(popupView);

            popupView.setVisibility(popupView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

    }
}
