package com.college.cs4048_group_007.viewmodel;

import androidx.lifecycle.ViewModel;

import com.college.cs4048_group_007.data.PoiRepository;
import com.college.cs4048_group_007.data.RideRepository;
import com.college.cs4048_group_007.entities.Poi;
import com.college.cs4048_group_007.entities.Ride;

public class RideViewModel extends ViewModel {
    private final RideRepository rideRepository;

    public RideViewModel(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public void insertRide(Ride ride) {
        rideRepository.insertPoi(ride);
    }
}
