package com.college.cs4048_group_007.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.college.cs4048_group_007.data.PoiRepository;

import org.jetbrains.annotations.NotNull;

public class PoiViewModelFactory implements ViewModelProvider.Factory{
    private final PoiRepository poiRepository;

    public PoiViewModelFactory(PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PoiViewModel.class)) {
            return (T) new PoiViewModel(poiRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
