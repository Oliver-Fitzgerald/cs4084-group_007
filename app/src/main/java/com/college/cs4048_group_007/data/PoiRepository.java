package com.college.cs4048_group_007.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.college.cs4048_group_007.entities.Poi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Repository fetches the Dao from AppDatabase and provides a API for viewModels and UI components
 */
public class PoiRepository {
    private final PoiDao poiDao;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public PoiRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        poiDao = db.poiDao();
    }

    public void insertPoi(Poi poi) {
        // Running insert operation on a background thread
        executor.execute(() -> poiDao.insert(poi));
    }

    public LiveData<Poi> getPoiById(int poiId) {
        // Room automatically runs it on a background thread because DAO returns LiveData<>
        return poiDao.getPoiById(poiId);
    }
}
