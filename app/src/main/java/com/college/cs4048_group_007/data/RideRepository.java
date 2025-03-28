package com.college.cs4048_group_007.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.college.cs4048_group_007.entities.Ride;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RideRepository {
    private final RideDao rideDao;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public RideRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        rideDao = db.rideDao();
    }

    public void insertPoi(Ride ride) {
        // Running insert operation on a background thread
        executor.execute(() -> rideDao.insert(ride));
    }

    public LiveData<Ride> getRideById(int rideId) {
        // Room automatically runs it on a background thread because DAO returns LiveData<>
        return rideDao.getRideById(rideId);
    }
}
