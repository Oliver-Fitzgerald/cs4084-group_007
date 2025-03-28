package com.college.cs4048_group_007.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.college.cs4048_group_007.entities.SaleItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaleItemRepository {
    private final SaleItemDao saleItemDao;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SaleItemRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        saleItemDao = db.saleItemDao();
    }

    public void insertSaleItem(SaleItem saleItem) {
        // Running insert operation on a background thread
        executor.execute(() -> saleItemDao.insert(saleItem));
    }

    public LiveData<SaleItem> getSaleItemById(int productId) {
        // Room automatically runs it on a background thread because DAO returns LiveData<>
        return saleItemDao.getSaleItemById(productId);
    }
}
