package com.college.cs4048_group_007.pathing;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

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

        for(int i = 0; i < path.size(); i++){
            ImageView imageView = new ImageView(context);
            Drawable drawable = loadImageFromAssets(path.get(i));

            Drawable wrappedDrawable = DrawableCompat.wrap(drawable).mutate();

            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, android.R.color.holo_red_dark));

            imageView.setImageDrawable(wrappedDrawable);

            frameLayout.addView(imageView);
        }

    }

    private Drawable loadImageFromAssets(String imagePath) {
        try {
            InputStream image = context.getAssets().open("paths/" + imagePath);
            return Drawable.createFromStream(image, null);
        } catch (Exception e) {
            Log.e("PATH_VISUALIZER", e.getMessage());
            return null;
        }
    }
}
