package com.college.cs4048_group_007;


import static java.lang.String.*;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.college.cs4048_group_007.data.AppDatabase;
import com.college.cs4048_group_007.data.PoiRepository;
import com.college.cs4048_group_007.data.RidePoi;
import com.college.cs4048_group_007.data.RideRepository;
import com.college.cs4048_group_007.fragments.Menu;
import com.college.cs4048_group_007.pathing.POI;
import com.college.cs4048_group_007.popup.ButtonPopupComponent;
import com.college.cs4048_group_007.popup.DescriptionPopupComponent;
import com.college.cs4048_group_007.popup.Popup;
import com.college.cs4048_group_007.viewmodel.PoiViewModel;
import com.college.cs4048_group_007.viewmodel.PoiViewModelFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class MapActivity extends AppCompatActivity {

    //Global Constants
    final static private String RESET = "\u001B[0m";
    final static private String RED = "\u001B[41m";
    final static private String MAP_ACTIVITY = "Map Activity"; //for autocomplete
    final static long DOUBLE_CLICK_THRESHOLD = 300; // Time in milliseconds
    final static long DOUBLE_CLICK_COOLDOWN = 800; //Time in milliseconds
    final static long[] lastClickTime = {System.currentTimeMillis()}; //must be array to persist in memory between touch events
    final static long[] lastDoubleClickTime = {System.currentTimeMillis()}; //must be array to persist in memory between touch events
    private static Map<String, Bitmap> mapPOIs = new HashMap<>();//Clickable Regions
    final private static Map<String, POI> textPOIs = new HashMap<>();//POI Text Regions
    private static String message;
    private static String loadBitmapThreadMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MAP_ACTIVITY,"onCreate() called");

        EdgeToEdge.enable(this);
        setContentView(R.layout.map_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.map_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            AppDatabase.insertTestData(db);
        });


        //Menu Button Listner
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            Log.i(MAP_ACTIVITY,"Menu Button Clicked");
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.map_container,Menu.class,null)
                    .commit();
        });
        mapPOIs = LoadingMap.mapPOIs;
    }

    final boolean[] popUpActive = {false};
    final View[] currentPopUp = new View[1];

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onResume() {
        super.onResume();
        Log.i(MAP_ACTIVITY, "onResume() called");

        FrameLayout poiTitleContainer = findViewById(R.id.map_container);

        //Retreive pre-generated BitMaps from loading
        /*
        Thread loadBitmaps = new Thread(() -> {
            loadBitmaps(getApplicationContext());
            Log.i(MAP_ACTIVITY,"Bitmaps Loaded");
        });
        loadBitmaps.start();
         */


        //Global listener for POI Bitmaps
        poiTitleContainer.setOnTouchListener((view, event) -> {


            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;

            //Check for valid click i.e Double Click to prevent slowing down main UI thread
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastDoubleClickTime[0] < DOUBLE_CLICK_COOLDOWN)
                return false;

            if (currentTime - lastClickTime[0] < DOUBLE_CLICK_THRESHOLD) {
                lastClickTime[0] = currentTime;
                lastDoubleClickTime[0] = System.currentTimeMillis();
            } else {
                // Single click detected
                lastClickTime[0] = currentTime;
                return false;
            }
            Log.i(MAP_ACTIVITY,"Double Click Registered");

            //if alive bitmaps have not loaded !!!Warning this prevents users from clicking anything until bitmap loading has completed
            /*
            if (loadBitmaps.isAlive()) {
                Toast.makeText(getApplicationContext(), "Map Still Loading ...", Toast.LENGTH_SHORT).show();
                return false;
            }
             */

            if (popUpActive[0])
                currentPopUp[0].setVisibility(View.GONE);

            //Get Touch Coordinates
            int x = (int) event.getX();
            int y = (int) event.getY();
            //Check if a not transparent section of any image has been selected
            for (Map.Entry<String, Bitmap> entry : mapPOIs.entrySet()) { // !!! Warning having to check each Bitmap is a slow operation which problems are somewhat mitigated by requiring a double click

                message = format("Checking if POI %s was clicked (clicked X: %d, clicked Y: %d, POI width: %d, POI height: %d)",entry.getKey(),x,y,entry.getValue().getWidth(),entry.getValue().getHeight());
                Log.d(MAP_ACTIVITY,message);
                // If not transparent, process the touch
                if (entry.getValue().getPixel(x, y) >>> 24 == 255 ) {
                    try {
                        message = format("Click registered on POI %s(clicked X: %d, clicked Y: %d, POI width: %d, POI height: %d)", entry.getKey(), x, y, entry.getValue().getWidth(), entry.getValue().getHeight());
                        Log.d(MAP_ACTIVITY, message);
                        Toast.makeText(getApplicationContext(), "You clicked " + entry.getKey(), Toast.LENGTH_SHORT).show();
                        //textOfPOI.cckPOI();
                        if (!popUpActive[0]) {
                            System.out.println("here");
                            currentPopUp[0] = getPopup(getApplicationContext(), x, y, entry.getKey());
                            popUpActive[0] = true;
                        } else
                            popUpActive[0] = false;

                        return true;
                    } catch (NullPointerException exception) {
                        message = format("Error getting equivalent text POI for the clicked POI %s", entry.getKey());
                        Log.e(MAP_ACTIVITY, message);
                    }
                }
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onPause() {
        super.onPause();
        Log.i(MAP_ACTIVITY, "onPaues() called");

        currentPopUp[0].setVisibility(View.GONE);
        popUpActive[0] = false;
        currentPopUp[0] = null;

    }
    /**
     * loadBitmaps
     *
     * @param context the context used to access application resources
     * Loads all images from the "assets/myImages" directory and generates their bitmaps.
     * The bitmaps are scaled to fit the screen height while preserving their original
     * aspect ratio.
     * These scaled bitmaps are used to define clickable areas on the screen.
     *
     *                !!! Warning !!!
     * This method should be ran in a separate thread from the main UI thread to prevent
     * slowing the main UI thread.
     * This also means that process dependant on these bitmaps are blocked until it's completion.
     * This should be improved.
     */
    public void loadBitmaps(Context context) {
        try {
            //Get all images in assets
            AssetManager assetManager = context.getAssets();
            String[] files = assetManager.list("myImages");
            assert files != null;

            System.out.println("Scaled Image Width: " + findViewById(R.id.map).getWidth());
            System.out.println("Scaled Image Height " + findViewById(R.id.map).getHeight());

            InputStream inputStream = null;
            for (String file : files) {
                //Convert all images to Bitmaps
                inputStream = assetManager.open("myImages/" + file);
                Bitmap mapPOI = BitmapFactory.decodeStream(inputStream);

                //Scale Bitmap
                mapPOI = Bitmap.createScaledBitmap(mapPOI, 3126, 2274, true);

                loadBitmapThreadMessage = format("Loading Bitmap from file %s in assets/myImages", file.substring(0, file.indexOf(".")));
                Log.i(MAP_ACTIVITY, loadBitmapThreadMessage);
                mapPOIs.put(file.substring(0, file.indexOf(".")), mapPOI);
                inputStream.close();
            }


        } catch (IOException exception) {
            loadBitmapThreadMessage = format("Error reading image assets from assets/myImages\n%s", exception.getMessage());
            Log.e(MAP_ACTIVITY, loadBitmapThreadMessage);
        }
    }


    public View getPopup(Context context, int x, int y, String attractName) {

        //Create Shop Button for popup
        ButtonPopupComponent buttonPopupComponent = new ButtonPopupComponent(context, R.layout.button_popup_component);

        //Build Pop up
        Popup popup = new Popup.Builder()
                .init(context)
                .setBase(R.layout.rollercoaster_base)
                .addComponent(new DescriptionPopupComponent(context)) // Popup POI Basic Info
                .addComponent(buttonPopupComponent) // Popup Shop Button
                .build();

        //DB DAO
        PoiRepository poiRepository = new PoiRepository(getApplication());
        PoiViewModelFactory factory = new PoiViewModelFactory(poiRepository);
        PoiViewModel poiViewModel = new ViewModelProvider(this, factory).get(PoiViewModel.class);

        //Get POI Object from attraction name
        poiViewModel.getIdByName(attractName).observe(this, poiId -> {

            VisitedTracker.markVisited(getApplicationContext(), String.valueOf(poiId)); // Mark this ride as visited
            int visitedCount = VisitedTracker.getVisitedCount(getApplicationContext());
            Toast.makeText(getApplicationContext(), "Visited " + visitedCount + " attractions!", Toast.LENGTH_SHORT).show();

            if (poiId != null) {
                // Log the ID to confirm data retrieval
                android.util.Log.d("MapActivity1", "ID fetched: " + poiId);
                android.util.Log.d("MapActivity1", "ID fetched: " + poiId);
                android.util.Log.d("MapActivity1", "attractName fetched: " + attractName);

                final String[] description = new String[1];
                poiViewModel.getPoiById(poiId).observe(this, poi -> {

                    //Add Info relevant for every poopup
                    if (poi != null) {
                        // Log the description to confirm data retrieval
                        android.util.Log.d("MapActivity1", "ridePoi.name fetched: " + poi.name);

                        description[0] = "" +
                                "Name: " + poi.name.replaceAll("_", " ") + "\n" +
                                "Description: " + poi.description + "\n" +
                                "Open & Close time: " + poi.openTime + "am--" + poi.closeTime + "pm\n";

                    }

                    //Add Info relevant for rides only
                    poiViewModel.getRidePoiById(poiId).observe(this, ridePoi -> {

                        Log.i("TESTING","Entered Ride POI");
                        if (ridePoi != null) Log.i("TESTING","Ride POI is null");
                        if (ridePoi != null) {

                            description[0] += "" +
                                    "Rating: " + ridePoi.rating + "\n" +
                                    "Current Waiting Time: " + ridePoi.queue_length + "\n";
                        }

                        android.util.Log.d("MapActivity2", "Description fetched: " + description[0]);
                        TextView popupTitle = popup.getView().findViewById(R.id.popup_title);

                        android.util.Log.d("MapActivity2", "popupTitle: " + popupTitle.getText());
                        popupTitle.setText(attractName.replaceAll("_"," "));

                        // Update the popup data dynamically
                        Map<String, Object> descriptionComponentData = Map.of("description", description[0]);

                        Map<String, Map<String, Object>> data = Map.of(
                                "description", descriptionComponentData
                        );
                        popup.update(data);
                    });
                });

            } else
                Log.e(MAP_ACTIVITY,"Error retrieving POI object from DB");
        });

        Button popupButton = buttonPopupComponent.getMyButton();
        if (popupButton != null) {
            popupButton.setOnClickListener(v -> {
                // Handle button click
                Toast.makeText(getApplicationContext(), "Button clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, FoodwaitingActivity.class);
                Toast.makeText(getApplicationContext(), "Food ordering!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            });
        } else {
            Log.e(MAP_ACTIVITY, "Button is null");
        }

        LinearLayout popupContainer = findViewById(R.id.popup_container);

        popupContainer.setX(x - 200);
        popupContainer.setY(y - 400);

        View popupView = popup.getView();

        if (popupView.getParent() != null)
            ((ViewGroup) popupView.getParent()).removeView(popupView);

        popupContainer.addView(popupView);

        popupView.setVisibility(View.VISIBLE);
        return popupView;
    }
}
