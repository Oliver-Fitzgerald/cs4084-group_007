package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.college.cs4048_group_007.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopupComponentGrid {
    private Map<String, PopupComponent> components;
    private View componentView;
    private LinearLayout componentListLayout;

    public PopupComponentGrid(Context context, int layoutResource) {
        this.components = new HashMap<String, PopupComponent>();

        this.componentView = LayoutInflater.from(context).inflate(layoutResource, null, false);
        this.componentListLayout = this.componentView.findViewById(R.id.component_grid);
    }

    public PopupComponentGrid(Context context){
        this(context, R.layout.popup_component_grid);
    }

    public View getComponentView() {
        return componentView;
    }

    public void addComponent(PopupComponent component){
        this.components.put(component.toString(), component);

        this.componentListLayout.addView(component.getComponentView());
    }

    public void update(Map<String, Map<String, Object>> data){
        for(Map.Entry<String, Map<String, Object>> entry : data.entrySet()){
            PopupComponent component = this.components.get(entry.getKey());

            if(component != null){
                component.update(entry.getValue());
            }
        }
    }
}
