package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.college.cs4048_group_007.R;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code PopupComponentGrid} class manages a collection of {@code PopupComponent} objects within the popup layout.
 * It handles adding new components to the grid and updating the components when new data is available.
 */
public class PopupComponentGrid {
    private Map<String, PopupComponent> components;
    private View componentView;
    private LinearLayout componentListLayout;

    /**
     * Constructs a {@code PopupComponentGrid} with the specified layout resource.
     *
     * @param context The {@code Context} used to inflate the grid layout.
     * @param layoutResource The resource ID of the layout to inflate.
     */
    public PopupComponentGrid(Context context, int layoutResource) {
        this.components = new HashMap<String, PopupComponent>();

        // Inflate the grid layout and set up the list layout
        this.componentView = LayoutInflater.from(context).inflate(layoutResource, null, false);
        this.componentListLayout = this.componentView.findViewById(R.id.component_grid);
    }

    /**
     * Constructs a {@code PopupComponentGrid} using a default layout resource.
     *
     * @param context The {@code Context} used to inflate the default grid layout.
     */
    public PopupComponentGrid(Context context){
        this(context, R.layout.popup_component_grid);
    }

    /**
     * Returns the view representing the component grid.
     *
     * @return The {@code View} of the component grid.
     */
    public View getComponentView() {
        return componentView;
    }

    /**
     * Adds a new {@code PopupComponent} to the grid.
     *
     * @param component The {@code PopupComponent} to add to the grid.
     */
    public void addComponent(PopupComponent component){
        // Add the component to the map and the layout
        this.components.put(component.toString(), component);
        this.componentListLayout.addView(component.getComponentView());
    }

    /**
     * Updates the components in the grid with new data.
     *
     * @param data A map where the key is the component identifier, and the value is a map of data used to update that component.
     */
    public void update(Map<String, Map<String, Object>> data){
        // Loop through all the entries in the data map and update the components
        for(Map.Entry<String, Map<String, Object>> entry : data.entrySet()){
            PopupComponent component = this.components.get(entry.getKey());

            if(component != null){
                component.update(entry.getValue());
            }
        }
    }
}
