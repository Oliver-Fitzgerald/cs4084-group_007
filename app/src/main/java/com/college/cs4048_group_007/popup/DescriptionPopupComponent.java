package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.college.cs4048_group_007.R;

import java.util.Map;

public class DescriptionPopupComponent extends PopupComponent{
    TextView descriptionView;

    public DescriptionPopupComponent(Context context, int layoutResource){
        super(context, layoutResource);

        this.descriptionView = this.getComponentView().findViewById(R.id.popup_description);
    }

    public DescriptionPopupComponent(Context context){
        this(context, R.layout.description_popup_component);
    }

    @Override
    public void update(Map<String, Object> updates) {
        if (updates.containsKey("description") && updates.get("description") instanceof String){
            descriptionView.setText((String) updates.get("description"));
        }
    }

    @NonNull
    @Override
    public String toString(){
        return "description";
    }
}
