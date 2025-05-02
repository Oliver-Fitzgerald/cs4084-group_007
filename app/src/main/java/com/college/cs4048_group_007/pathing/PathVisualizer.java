package com.college.cs4048_group_007.pathing;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

class PathVisualizer {
    private Context context;
    private FrameLayout frameLayout;

    public PathVisualizer(Context context, FrameLayout frameLayout) {
        this.context = context;
        this.frameLayout = frameLayout;
    }

    public void displayPath(List<String> path, PathGraph graph) {
        frameLayout.removeAllViews(); // Clear existing views

        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);

            for (PathGraph.Edge edge : graph.getNeighbors(from)) {
                if (edge.to.equals(to)) {
                    ImageView imageView = new ImageView(context);
                    imageView.setImageDrawable(loadImageFromAssets(edge.imagePath));
                    frameLayout.addView(imageView); // Add the image to the FrameLayout
                    break;
                }
            }
        }
    }

    private Drawable loadImageFromAssets(String imagePath) {
        try {
            return Drawable.createFromStream(context.getAssets().open(imagePath), null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
