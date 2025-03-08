package com.college.cs4048_group_007.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.college.cs4048_group_007.R;

import java.util.Map;

/**
 * The Base Popup Class, which constructs a popup from a base layout xml and the popup component's.
 */
public class Popup {
    protected View popupView;
    protected Context context;
    protected PopupComponentGrid componentGrid;

    private Popup(Builder builder) {
        this.popupView = builder.popupView;
        this.context = builder.context;
        this.componentGrid = builder.componentGrid;

        ConstraintLayout layout = this.popupView.findViewById(R.id.rollercoaster_layout);
        layout.addView(componentGrid.getComponentView());

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) componentGrid.getComponentView().getLayoutParams();
        layoutParams.topToBottom = R.id.popup_title;

        componentGrid.getComponentView().setLayoutParams(layoutParams);

        this.popupView.setVisibility(View.GONE);
    }

    public View getView(){
        return this.popupView;
    }

    public void update(Map<String, Map<String, Object>> data) {
        this.componentGrid.update(data);
    }

    public static class Builder {
        private PopupComponentGrid componentGrid;
        private View popupView;
        private Context context;

        public Builder init(Context context) {
            this.context = context;
            return this;
        }

        public Builder setBase(int layoutResource) {
            // Inflate only once here, and we don't need to worry about view recycling
            this.popupView = LayoutInflater.from(this.context).inflate(layoutResource, null, false);
            this.componentGrid = new PopupComponentGrid(this.context);

            return this;
        }

        public Builder addComponent(PopupComponent component) {
            this.componentGrid.addComponent(component);

            return this;
        }

        public Popup build() {
            return new Popup(this);
        }
    }
}
