package com.college.cs4048_group_007.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.college.cs4048_group_007.MainActivity;
import com.college.cs4048_group_007.Profile;
import com.college.cs4048_group_007.R;
import com.google.android.material.button.MaterialButton;

public class Menu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.menu_layout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view,savedInstance);

        ImageButton closeMenuButton = view.findViewById(R.id.close_menu_button);
        closeMenuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               getFragmentManager().beginTransaction().remove(Menu.this).commit() ;
            }
        });


        Button homeButton = view.findViewById(R.id.menu_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(Menu.this).commit() ;
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button profileButton = view.findViewById(R.id.menu_profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("Menu","Profile Button Cllicked");
                getFragmentManager().beginTransaction().remove(Menu.this).commit() ;
                Intent intent = new Intent(getContext(), Profile.class);
                startActivity(intent);
            }
        });
    }
}