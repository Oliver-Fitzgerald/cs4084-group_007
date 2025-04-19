package com.college.cs4048_group_007.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.college.cs4048_group_007.data.PoiRepository;
import com.college.cs4048_group_007.data.RideRepository;

import org.jetbrains.annotations.NotNull;

public class RideViewModelFactory implements ViewModelProvider.Factory {
    private final RideRepository rideRepository;

    public RideViewModelFactory(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RideViewModel.class)) {
            return (T) new RideViewModel(rideRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
