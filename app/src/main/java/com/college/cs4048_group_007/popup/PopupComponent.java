package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Map;

/**
 * An abstract class representing a component within a popup. The component can be a UI element such as a button or text field,
 * and it can be updated with data when the popup is refreshed.
 */
public abstract class PopupComponent {
    private View componentView;

    /**
     * Constructs a {@code PopupComponent} with the specified layout resource.
     *
     * @param context The {@code Context} used to inflate the component layout.
     * @param layoutResource The layout resource ID to inflate.
     */
    public PopupComponent(Context context, int layoutResource){
        this.componentView = LayoutInflater.from(context).inflate(layoutResource, null, false);
    }

    /**
     * Default constructor for the component. The context and layout resource are not specified.
     * Typically used for components that may not have a layout resource.
     */
    public PopupComponent(){
        this(null, 0);
    }

    /**
     * Returns the view representing the component.
     *
     * @return The {@code View} of the component.
     */
    public View getComponentView() {
        return componentView;
    }

    /**
     * Updates the component with the provided data.
     *
     * @param updates A map of data that will be used to update the component.
     *               The exact structure of the data depends on the component's implementation.
     */
    public abstract void update(Map<String, Object> updates);

    /**
     * Returns a string representation of the component, typically used as a unique identifier.
     *
     * @return A string representation of the component.
     */
    public abstract String toString();
}
