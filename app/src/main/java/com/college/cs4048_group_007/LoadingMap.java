package com.college.cs4048_group_007;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class LoadingMap extends AppCompatActivity {
    final private static String LOADING_ACTIVITY = "Loading";
    public static Map<String, Bitmap> mapPOIs = new HashMap<>();//Clickable Regions
    private static ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.loading_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_loading), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progress_bar);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String intentPurpose = intent.getStringExtra("load");

        if (intentPurpose != null && intentPurpose.equals("bitmaps")) {
            //Pre-Generate BitMaps in a separate thread to prevent slowing the main UI thread
            Thread loadBitmaps = new Thread(() -> {
                loadBitmaps(getApplicationContext());
                progressBar.setProgress(100);
                Log.i(LOADING_ACTIVITY, "Bitmaps Loaded");
                Intent nextIntent = new Intent(this, MapActivity.class);
                String userType  = intent.getStringExtra("userType");
                System.out.println("Loading user Type: " + userType);
                nextIntent.putExtra("userType",userType);
                startActivity(nextIntent);
            });
            loadBitmaps.start();
        } else {
            TextView loadingText = findViewById(R.id.loading_text);
            loadingText.setText("You have navigated to this actibity from somewhere unexpected");
        }
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
            for (int i = 0; i < files.length; i++) {
                //Convert all images to Bitmaps
                inputStream = assetManager.open("myImages/" + files[i]);
                Bitmap mapPOI = BitmapFactory.decodeStream(inputStream);

                //Scale Bitmap
                mapPOI = Bitmap.createScaledBitmap(mapPOI, 3126, 2274, true);

                String message = format("Loading Bitmap from file %s in assets/myImages", files[i].substring(0, files[i].indexOf(".")));
                Log.i(LOADING_ACTIVITY, message);
                mapPOIs.put(files[i].substring(0, files[i].indexOf(".")), mapPOI);
                inputStream.close();

                progressBar.setProgress( (int) Math.round((i * 1.667))); //Division is to slow
            }


        } catch (IOException exception) {
            String message = format("Error reading image assets from assets/myImages\n%s", exception.getMessage());
            Log.e(LOADING_ACTIVITY, message);
        }
    }
}
