package com.college.cs4048_group_007.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.college.cs4048_group_007.entities.Poi;
import com.college.cs4048_group_007.entities.Ride;

import java.util.List;

@Dao
public interface RideDao {
    @Insert
    Long insert(Ride Ride);

    @Query("SELECT * FROM ride")
    LiveData<List<Ride>> getAllRide();

    @Query("SELECT * FROM ride WHERE ride_id = :rideId")
    LiveData<Ride> getRideById(int rideId);

    @Update
    int updateRide(Ride ride);

    @Delete
    int deleteRide(Ride ride);
}
