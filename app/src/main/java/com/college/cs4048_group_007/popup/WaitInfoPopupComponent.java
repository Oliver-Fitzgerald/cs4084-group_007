package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.college.cs4048_group_007.R;

import java.util.Map;

/**
 * The {@code WaitInfoPopupComponent} class represents a specific popup component that displays information about the wait time
 * in a {@code TextView}. This component is designed to be part of a larger popup layout and can be updated with new wait time data.
 *
 * <p>The component uses a {@code TextView} to display the length of the line or wait time, which can be updated when new data is passed.</p>
 */
public class WaitInfoPopupComponent extends PopupComponent {
    private TextView lineLengthView;

    /**
     * Constructs a {@code WaitInfoPopupComponent} with the specified layout resource.
     *
     * @param context The {@code Context} used to inflate the component layout.
     * @param layoutResource The layout resource ID to inflate.
     */
    public WaitInfoPopupComponent(Context context, int layoutResource){
        super(context, layoutResource);
        this.lineLengthView = this.getComponentView().findViewById(R.id.line_length);
    }

    /**
     * Constructs a {@code WaitInfoPopupComponent} using a default layout resource.
     *
     * @param context The {@code Context} used to inflate the default component layout.
     */
    public WaitInfoPopupComponent(Context context){
        this(context, R.layout.wait_popup_component);
    }

    /**
     * Updates the wait information component with the provided data.
     *
     * @param updates A map containing the data to update the component.
     *               The key {@code "lineLength"} should contain an {@code Integer} value representing the wait time or line length,
     *               which will be displayed in the {@code TextView}.
     */
    @Override
    public void update(Map<String, Object> updates) {
        if (updates.containsKey("lineLength") && updates.get("lineLength") instanceof Integer){
            lineLengthView.setText(String.format("%d", (Integer) updates.get("lineLength")));
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
        return "wait-info";
    }
}
