package com.college.cs4048_group_007.pathing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

/**
 * POI (Point Of Interest)
 * Represents an image in an activity. Provides functionality to support pathing through an inner class WeightedEdges.
 */
public class POI extends AppCompatImageView {
    private static boolean poiCurrentlyClicked = false;
    private static POI currentylSelectedPOI;

    public POI(Context context){
        super(context);
        //so onTouch events can only be triggered programmatically
        setFocusable(false);
        setClickable(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    public POI(Context context, AttributeSet attrs) {
        super(context, attrs);
        //so onTouch events can only be triggered programmatically
        setFocusable(false);
        setClickable(false);
    }

    public POI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //so onTouch events can only be triggered programmatically
        setFocusable(false);
        setClickable(false);
    }

    public void clickPOI() { //!!! WARNING May need to add functionality in future to allow custom listeners on POI's

        Log.i("POI Listner","onTouch event");
        //Re-set last touch event on a POI if on exists
        if (poiCurrentlyClicked) {
            currentylSelectedPOI.resetTouchEvent();
            if (currentylSelectedPOI.equals(this))
                return;
        }
        Log.i("POI Listener","onTouch event registered on POI");

        animate()
            .scaleX(1.5f)
            .scaleY(1.5f)
            .translationX(685f)
            .translationY(-290f)
            .setDuration(300)
            .start();

        poiCurrentlyClicked = true;
        currentylSelectedPOI = this;
    }

    private void resetTouchEvent(){
        poiCurrentlyClicked = false;
        currentylSelectedPOI = null;
        Log.i("POI Listener","Resetting Last POI Selection");
        // Scale back to original size and reset translation
        animate()
            .scaleX(1f)            // Reset width
            .scaleY(1f)            // Reset height
            .translationX(0f)      // Reset translation X
            .translationY(0f)      // Reset translation Y
            .setDuration(300)
            .start();

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
