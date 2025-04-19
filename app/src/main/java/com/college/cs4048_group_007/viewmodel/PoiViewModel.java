package com.college.cs4048_group_007.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.college.cs4048_group_007.data.PoiRepository;
import com.college.cs4048_group_007.data.RidePoi;
import com.college.cs4048_group_007.entities.Poi;

// ViewModel for POI data
// This class can be used to store and manage UI-related data in a lifecycle-conscious way.
public class PoiViewModel extends ViewModel {

    private final PoiRepository poiRepository;

    public PoiViewModel(PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    public void insertPoi(Poi poi) {
        poiRepository.insertPoi(poi);
    }

    public void updatePoi(Poi poi) {
        poiRepository.updatePoi(poi);
    }

    public void deletePoi(Poi poi) {
        poiRepository.deletePoi(poi);
    }

    public void deleteAllPoi() {
        poiRepository.deleteAllPoi();
    }

    public LiveData<Poi> getPoiById(int poiId) {
        return poiRepository.getPoiById(poiId);
    }

    public LiveData<Integer> getIdByName(String name) {
        return poiRepository.getIdByName(name);
    }

    public LiveData<RidePoi> getRidePoiById(int poiId) {
        return poiRepository.getRidePoiById(poiId);
    }
}
