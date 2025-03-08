package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.college.cs4048_group_007.R;

import java.util.Map;

/**
 * The {@code DescriptionPopupComponent} class represents a specific popup component that displays a description in a {@code TextView}.
 * This component is designed to be part of a larger popup layout and is capable of updating its content when new data is provided.
 *
 * <p>The component uses a {@code TextView} to display a description, and it can be updated with a string representing the description content.</p>
 */
public class DescriptionPopupComponent extends PopupComponent {
    private TextView descriptionView;

    /**
     * Constructs a {@code DescriptionPopupComponent} with the specified layout resource.
     *
     * @param context The {@code Context} used to inflate the component layout.
     * @param layoutResource The layout resource ID to inflate.
     */
    public DescriptionPopupComponent(Context context, int layoutResource){
        super(context, layoutResource);
        this.descriptionView = this.getComponentView().findViewById(R.id.popup_description);
    }

    /**
     * Constructs a {@code DescriptionPopupComponent} using a default layout resource.
     *
     * @param context The {@code Context} used to inflate the default component layout.
     */
    public DescriptionPopupComponent(Context context){
        this(context, R.layout.description_popup_component);
    }

    /**
     * Updates the description component with the provided data.
     *
     * @param updates A map containing the data to update the component.
     *               The key {@code "description"} should contain a {@code String} value that will be set to the description view.
     */
    @Override
    public void update(Map<String, Object> updates) {
        if (updates.containsKey("description") && updates.get("description") instanceof String){
            descriptionView.setText((String) updates.get("description"));
        }
    }

    /**
     * Returns a string representation of the component, typically used as a unique identifier for this component.
     *
     * @return A string representing the component's identifier.
     */
    @NonNull
    @Override
    public String toString(){
        return "description";
    }
}
