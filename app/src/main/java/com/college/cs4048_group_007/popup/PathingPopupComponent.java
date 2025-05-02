package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.college.cs4048_group_007.pathing.PathFinder;
import com.college.cs4048_group_007.pathing.PathGraph;
import com.college.cs4048_group_007.pathing.PathVisualizer;
import com.college.cs4048_group_007.R;

import java.util.List;
import java.util.Map;

/**
 * The {@code PathingPopupComponent} class represents a specific popup component that allows users to find and display the shortest path
 * to a specified attraction. It uses shared instances of {@code PathGraph} and {@code PathFinder} to ensure efficiency.
 */
public class PathingPopupComponent extends PopupComponent {

    private EditText rideNameInput;
    private Button findPathButton;
    private FrameLayout pathFrameLayout;
    private Context context;

    private final PathGraph pathGraph;
    private final PathFinder pathFinder;

    /**
     * Constructs a {@code PathingPopupComponent} using a default layout resource and shared graph/finder instances.
     *
     * @param context The {@code Context} used to inflate the default component layout.
     * @param pathGraph The shared {@code PathGraph} instance.
     * @param pathFinder The shared {@code PathFinder} instance.
     */
    public PathingPopupComponent(Context context, PathGraph pathGraph, PathFinder pathFinder, FrameLayout pathFrameLayout) {
        super(context, R.layout.pathing_popup_component);
        this.context = context;
        this.pathGraph = pathGraph;
        this.pathFinder = pathFinder;
        this.pathFrameLayout = pathFrameLayout;
        initializeViews();
    }

    /**
     * Initializes the views used in this component.
     */
    private void initializeViews() {
        rideNameInput = getComponentView().findViewById(R.id.ride_name_input);
        findPathButton = getComponentView().findViewById(R.id.find_path_button);

        // Set up the button click listener
        findPathButton.setOnClickListener(v -> findAndDisplayPath());
    }

    /**
     * Finds the shortest path to the specified attraction and displays it on the {@code FrameLayout}.
     */
    private void findAndDisplayPath() {
        Log.i("PATHING_POPUP", "clicked");
        String attractionName = rideNameInput.getText().toString().trim();

        if (attractionName.isEmpty()) {
            Toast.makeText(context, "Please enter a ride name!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Find the shortest path using the shared PathFinder
        List<String> shortestPath = pathFinder.getShortestPath("carousel", attractionName);

        if (shortestPath == null || shortestPath.isEmpty()) {
            Toast.makeText(context, "Ride not found!", Toast.LENGTH_SHORT).show();
            pathFrameLayout.removeAllViews(); // Clear the FrameLayout
            return;
        }

        // Visualize the path using the shared PathGraph
        PathVisualizer visualizer = new PathVisualizer(context, pathFrameLayout);
        visualizer.displayPath(shortestPath, pathGraph);
    }

    /**
     * Updates the component with the provided data.
     *
     * @param updates A map containing the data to update the component.
     *                Currently, no updates are supported for this component.
     */
    @Override
    public void update(Map<String, Object> updates) {
        // No updates supported for this component at this time
    }

    /**
     * Returns a string representation of the component, typically used as a unique identifier for this component.
     *
     * @return A string representing the component's identifier.
     */
    @NonNull
    @Override
    public String toString() {
        return "pathing";
    }
}