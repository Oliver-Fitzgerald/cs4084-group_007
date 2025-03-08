package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Map;

public abstract class PopupComponent {
    private View componentView;

    public PopupComponent(Context context, int layoutResource){
        this.componentView = LayoutInflater.from(context).inflate(layoutResource, null, false);
    }

    public PopupComponent(){
        this(null, 0);
    }

    public View getComponentView() {
        return componentView;
    }

    public abstract void update(Map<String, Object> updates);

    public abstract String toString();
}
