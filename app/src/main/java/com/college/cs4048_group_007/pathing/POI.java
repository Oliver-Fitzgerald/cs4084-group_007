package com.college.cs4048_group_007.pathing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.college.cs4048_group_007.R;

import java.util.HashMap;
import java.util.Map;

/**
 * POI (Point Of Interest)
 * Represents an image in an activity. Provides functionality to support pathing through an inner class WeightedEdges.
 */
@Entity(tableName = "poi")
public class POI {

    public int poiId;

    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "open_time")
    public String openDate;
    @ColumnInfo(name = "close_time")
    public String closeTime;
    @ColumnInfo(name = "type")
    public String type;

    private static boolean poiCurrentlyClicked = false;
    private static POI currentylSelectedPOI;

    public void clickPOI() { //!!! WARNING May need to add functionality in future to allow custom listeners on POI's

        Log.i("POI Listner","onTouch event");
        //Re-set last touch event on a POI if on exists
        if (poiCurrentlyClicked) {
            currentylSelectedPOI.resetTouchEvent();
            if (currentylSelectedPOI.equals(this))
                return;
        }
        Log.i("POI Listener","onTouch event registered on POI");

        poiCurrentlyClicked = true;
        currentylSelectedPOI = this;
    }

    private void resetTouchEvent(){
        poiCurrentlyClicked = false;
        currentylSelectedPOI = null;
        Log.i("POI Listener","Resetting Last POI Selection");
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