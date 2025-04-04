package com.college.cs4048_group_007.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.college.cs4048_group_007.entities.Ride;
import com.college.cs4048_group_007.entities.SaleItem;

import java.util.List;

@Dao
public interface SaleItemDao {
    @Insert
    Long insert(SaleItem saleItem);

    @Query("SELECT * FROM sale_item")
    LiveData<List<SaleItem>> getAllSaleItem();

    @Query("SELECT * FROM sale_item WHERE product_id = :productId")
    LiveData<SaleItem> getSaleItemById(int productId);

    @Update
    int updateSaleItem(SaleItem saleItem);

    @Delete
    int deleteSaleItem(SaleItem saleItem);
}
