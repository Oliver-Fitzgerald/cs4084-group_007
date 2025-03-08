package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.college.cs4048_group_007.R;

import java.util.Map;

public class WaitInfoPopupComponent extends PopupComponent{
    TextView lineLengthView;

    public WaitInfoPopupComponent(Context context, int layoutResource){
        super(context, layoutResource);

        this.lineLengthView = this.getComponentView().findViewById(R.id.line_length);
    }

    public WaitInfoPopupComponent(Context context){
        this(context, R.layout.wait_popup_component);
    }

    @Override
    public void update(Map<String, Object> updates) {
        if (updates.containsKey("lineLength") && updates.get("lineLength") instanceof Integer){
            lineLengthView.setText(String.format("%d", (Integer) updates.get("lineLength")));
        }
    }

    @NonNull
    @Override
    public String toString(){
        return "wait-info";
    }
}
