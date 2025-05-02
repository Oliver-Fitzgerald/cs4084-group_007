package com.college.cs4048_group_007.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.college.cs4048_group_007.entities.Poi;

import java.util.List;
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

    public void updatePoi(Poi poi) {
        // Running update operation on a background thread
        executor.execute(() -> poiDao.updatePoi(poi));
    }

    public void deletePoi(Poi poi) {
        // Running delete operation on a background thread
        executor.execute(() -> poiDao.deletePoi(poi));
    }

    public void deleteAllPoi() {
        // Running delete operation on a background thread
        executor.execute(poiDao::deleteAllPoi);
    }

    public LiveData<Poi> getPoiById(int poiId) {
        // Room automatically runs it on a background thread because DAO returns LiveData<>
        return poiDao.getPoiById(poiId);

    }

    public LiveData<Integer> getIdByName(String name) {
        // Room automatically runs it on a background thread because DAO returns LiveData<>
        return poiDao.getIdByName(name);
    }

    public LiveData<RidePoi> getRidePoiById(int poiId) {
        // Room automatically runs it on a background thread because DAO returns LiveData<>

        return poiDao.getRidePoiById(poiId);
    }
}
