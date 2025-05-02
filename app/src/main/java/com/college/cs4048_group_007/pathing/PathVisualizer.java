package com.college.cs4048_group_007.pathing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.List;

public class PathVisualizer {
    private Context context;
    private FrameLayout frameLayout;

    public PathVisualizer(Context context, FrameLayout frameLayout) {
        this.context = context;
        this.frameLayout = frameLayout;
    }

    public void displayPath(List<String> path, PathGraph graph) {
        frameLayout.removeAllViews(); // Clear existing views

        Log.i("PATH_VISUALIZER", path.get(0));

        for (int i = 0; i < path.size(); i++) {
            ImageView imageView = new ImageView(context);
            Bitmap bitmap = loadImageFromAssets(path.get(i));

            TintedDrawable redTintDrawable =
                    new TintedDrawable(
                            bitmap,
                            Color.argb(0.5f, 1.0f, 0.0f, 0.0f));

            imageView.setImageDrawable(redTintDrawable);


            frameLayout.addView(imageView);
        }
    }

    private Bitmap loadImageFromAssets(String imagePath) {
        try {
            InputStream image = context.getAssets().open("paths/" + imagePath);
            return BitmapFactory.decodeStream(image);
        } catch (Exception e) {
            Log.e("PATH_VISUALIZER", e.getMessage());
            return null;
        }
    }
}
