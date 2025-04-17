package com.college.cs4048_group_007;


import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.college.cs4048_group_007.pathing.POI;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MapActivity extends AppCompatActivity  {

    //Global Constants
    final static private String RESET = "\u001B[0m";
    final static private String RED = "\u001B[41m";
    final static private String MAP_ACTIVITY = "Map Activity"; //for autocomplete
    final static long DOUBLE_CLICK_THRESHOLD = 300; // Time in milliseconds
    final static long DOUBLE_CLICK_COOLDOWN = 800; //Time in milliseconds
    final static long[] lastClickTime = {System.currentTimeMillis()}; //must be array to persist in memory between touch events
    final static long[] lastDoubleClickTime = {System.currentTimeMillis()}; //must be array to persist in memory between touch events
    final private static Map<String,Bitmap> mapPOIs = new HashMap<>();//Clickable Regions
    final private static Map<String,POI> textPOIs = new HashMap<>();//POI Text Regions
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


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onResume(){
        super.onResume();
        Log.i(MAP_ACTIVITY,"onResume() called");

        FrameLayout poiTitleContainer = findViewById(R.id.title_container);//Holds the text describing each poi on the map

        //Pre-Generate BitMaps in a separate thread to prevent slowing the main UI thread
        Thread loadBitmaps = new Thread(() -> {
            loadBitmaps(getApplicationContext());
            Log.i(MAP_ACTIVITY,"Bitmaps Loaded");
        });
        loadBitmaps.start();

        //The text describing each POI on the map is stored as a POI Object
        //Here we get each of them from the map_layout to be used later for animations
        for (int i = 0; i < poiTitleContainer.getChildCount(); i++)
            if (poiTitleContainer.getChildAt(i).getClass() == POI.class) {
                message = format("Adding Text POI %s from container %s to Text POIs", getResources().getResourceEntryName(poiTitleContainer.getChildAt(i).getId()),getResources().getResourceEntryName(poiTitleContainer.getId()));
                Log.i(MAP_ACTIVITY, message);
                textPOIs.put(getResources().getResourceEntryName(poiTitleContainer.getChildAt(i).getId()),(POI) poiTitleContainer.getChildAt(i));
            } else {
                message = format("Error Adding Text POI %s from container %s to Text POIs layout element is not of class POI", getResources().getResourceEntryName(poiTitleContainer.getChildAt(i).getId()),getResources().getResourceEntryName(poiTitleContainer.getId()));
                Log.e(MAP_ACTIVITY, message);
            }

        //Global listener for POI Bitmaps
        poiTitleContainer.setOnTouchListener((view, event)-> {

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
            if (loadBitmaps.isAlive()) {
                Toast.makeText(getApplicationContext(), "Map Still Loading ...", Toast.LENGTH_SHORT).show();
                return false;
            }

            //Get Touch Coordinates
            int x = (int) event.getX();
            int y = (int) event.getY();
            //Check if a not transparent section of any image has been selected
            for (Map.Entry<String,Bitmap> entry: mapPOIs.entrySet()) { // !!! Warning having to check each Bitmap is a slow operation which problems are somewhat mitigated by requiring a double click

                message = format("Checking if POI %s was clicked (clicked X: %d, clicked Y: %d, POI width: %d, POI height: %d)",entry.getKey(),x,y,entry.getValue().getWidth(),entry.getValue().getHeight());
                Log.d(MAP_ACTIVITY,message);
                // If not transparent, process the touch
                if (entry.getValue().getPixel(x, y) >>> 24 == 255 ) {
                    POI textOfPOI = textPOIs.get(entry.getKey() + "_text");
                    try {
                        message = format("Click registered on POI %s(clicked X: %d, clicked Y: %d, POI width: %d, POI height: %d)",entry.getKey(),x,y,entry.getValue().getWidth(),entry.getValue().getHeight());
                        Log.d(MAP_ACTIVITY,message);
                        textOfPOI.clickPOI();
                        return true;
                    } catch (NullPointerException exception) {
                        message = format("Error getting equivalent text POI for the clicked POI %s",entry.getKey());
                        Log.e(MAP_ACTIVITY,message);
                    }
                }
            }
            return false;
        });
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

            InputStream inputStream = null;
            for (String file : files) {
                //Convert all images to Bitmaps
                inputStream = assetManager.open("myImages/" + file);
                Bitmap mapPOI = BitmapFactory.decodeStream(inputStream);

                //Scale Bitmap
                int screenHeight = getApplication().getResources().getDisplayMetrics().heightPixels;
                float scale = (float) screenHeight / mapPOI.getHeight();
                int scaledBitmapWidth = (int) Math.ceil(scale * mapPOI.getWidth());

                mapPOI = Bitmap.createScaledBitmap(mapPOI, scaledBitmapWidth, screenHeight, true);

                loadBitmapThreadMessage = format("Loading Bitmap from file %s in assets/myImages", file.substring(0, file.indexOf(".")));
                Log.i(MAP_ACTIVITY, loadBitmapThreadMessage);
                mapPOIs.put(file.substring(0, file.indexOf(".")), mapPOI);
            }
            inputStream.close();

        } catch (IOException exception) {
            loadBitmapThreadMessage = format("Error reading image assets from assets/myImages\n%s",exception.getMessage());
            Log.e(MAP_ACTIVITY,loadBitmapThreadMessage);
        }
    }
}
