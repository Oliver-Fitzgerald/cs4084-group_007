package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.college.cs4048_group_007.R;

import java.util.Map;

public class ButtonPopupComponent extends PopupComponent {

    private TextView buttonView;
    private Button myButton;

    /**
     * Constructs a {@code DescriptionPopupComponent} with the specified layout resource.
     *
     * @param context The {@code Context} used to inflate the component layout.
     * @param layoutResource The layout resource ID to inflate.
     */
    public ButtonPopupComponent(Context context, int layoutResource){
        super(context, layoutResource);
        this.buttonView = this.getComponentView().findViewById(R.id.popup_button);
        this.myButton = this.getComponentView().findViewById(R.id.popup_button);
        Log.i("BUTTON_POPUP", "new button popup");
    }
    public void setText(String text) {
        myButton.setText(text);
    }

    /**
     * Constructs a {@code DescriptionPopupComponent} using a default layout resource.
     *
     * @param context The {@code Context} used to inflate the default component layout.
     */
    public ButtonPopupComponent(Context context){
        this(context, R.layout.button_popup_component);
    }

    /**
     * Updates the description component with the provided data.
     *
     * @param updates A map containing the data to update the component.
     *               The key {@code "description"} should contain a {@code String} value that will be set to the description view.
     */
    @Override
    public void update(Map<String, Object> updates) {
        if (updates.containsKey("button") && updates.get("button") instanceof String){
            buttonView.setText((String) updates.get("button"));
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
        return "button";
    }

    public Button getMyButton() {
        return myButton;
    }

}