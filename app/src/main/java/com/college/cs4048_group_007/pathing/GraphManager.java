package com.college.cs4048_group_007.pathing;

import com.college.cs4048_group_007.R;

import android.content.Context;

import java.util.Map;

public class GraphManager {
    private static GraphManager instance;
    private PathGraph pathGraph;
    private Map<String, PathFinder> pathFinders;

    private GraphManager(Context context) {
        // Private constructor to prevent instantiation
    }

    public static synchronized GraphManager getInstance(Context context) {
        if (instance == null) {
            instance = new GraphManager(context.getApplicationContext());
        }
        return instance;
    }

    public PathGraph getPathGraph() {
        return pathGraph;
    }

    public void setPathGraph(PathGraph pathGraph) {
        this.pathGraph = pathGraph;
    }

    public PathFinder getPathFinder(String attractionName) {
        return pathFinders.get(attractionName);
    }

    public void setPathFinders(Map<String, PathFinder> pathFinders) {
        this.pathFinders = pathFinders;
    }
}