package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.college.cs4048_group_007.R;

import java.util.Map;

/**
 * The {@code Popup} class represents a customizable popup. It constructs a popup view using a base layout XML and components
 * that are added to the layout. The popup can be updated with data through its components.
 *
 * <p>This class provides a method to get the popup view, update its components, and customize its layout by adding components
 * to a grid.</p>
 */
public class Popup {
    protected View popupView;
    protected Context context;
    protected PopupComponentGrid componentGrid;

    /**
     * Private constructor for the Popup class, used by the {@code Builder} class to construct a popup.
     *
     * @param builder The {@code Builder} object containing the settings to create the Popup.
     */
    private Popup(Builder builder) {
        this.popupView = builder.popupView;
        this.context = builder.context;
        this.componentGrid = builder.componentGrid;

        // Retrieve the layout and add the component grid to it
        ConstraintLayout layout = this.popupView.findViewById(R.id.rollercoaster_layout);
        layout.addView(componentGrid.getComponentView());

        // Set the layout parameters for the component to position it below the popup title
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) componentGrid.getComponentView().getLayoutParams();
        layoutParams.topToBottom = R.id.popup_title;
        componentGrid.getComponentView().setLayoutParams(layoutParams);

        // Initially hide the popup view
        this.popupView.setVisibility(View.GONE);
    }

    /**
     * Returns the root view of the popup.
     *
     * @return The root {@code View} of the popup layout.
     */
    public View getView(){
        return this.popupView;
    }

    /**
     * Updates the components of the popup with new data.
     *
     * @param data A map of keys to values, where each key corresponds to a {@code PopupComponent} and its associated data is passed
     *             to the {@code update} method of that component.
     */
    public void update(Map<String, Map<String, Object>> data) {
        this.componentGrid.update(data);
    }

    /**
     * The builder class for creating a {@code Popup} instance.
     *
     * <p>This class allows for step-by-step configuration of a popup by setting the base layout,
     * adding components, and finally building the {@code Popup} object.</p>
     */
    public static class Builder {
        private PopupComponentGrid componentGrid;
        private View popupView;
        private Context context;

        /**
         * Initializes the builder with the application context.
         *
         * @param context The {@code Context} used to inflate layouts and access resources.
         * @return The current {@code Builder} instance to allow method chaining.
         */
        public Builder init(Context context) {
            Log.i("POPUP", "init");
            this.context = context;
            return this;
        }

        /**
         * Sets the base layout resource for the popup.
         *
         * @param layoutResource The resource ID of the layout to inflate for the popup.
         * @return The current {@code Builder} instance to allow method chaining.
         */
        public Builder setBase(int layoutResource) {
            // Inflate the base layout and set up the component grid
            this.popupView = LayoutInflater.from(this.context).inflate(layoutResource, null, false);
            this.componentGrid = new PopupComponentGrid(this.context);
            Log.i("POPUP", "setBase");
            return this;
        }

        /**
         * Adds a {@code PopupComponent} to the popup.
         *
         * @param component The {@code PopupComponent} to add to the popup's component grid.
         * @return The current {@code Builder} instance to allow method chaining.
         */
        public Builder addComponent(PopupComponent component) {
            this.componentGrid.addComponent(component);

            return this;
        }

        /**
         * Builds and returns the configured {@code Popup} object.
         *
         * @return A new {@code Popup} instance with the settings from the builder.
         */
        public Popup build() {
            return new Popup(this);
        }
    }
}
