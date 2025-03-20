package com.college.cs4048_group_007.pathing;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * POI (Point Of Interest)
 * Represents an image in an activity. Provides functionality to support pathing through an inner class WeightedEdges.
 */
public class POI extends AppCompatImageView {
    public POI(Context context){
        super(context);
    }

    public POI(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public POI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public class WeightedEdges {
        private Map<POI, Integer> edges = new HashMap<>();
        public void addEdge(POI pointOfInterest, Integer weight){
            edges.put(pointOfInterest, weight);
        }
        public Map<POI, Integer> getWeightedEdges(){
            return edges;
        }
    }

}
