package com.college.cs4048_group_007.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.college.cs4048_group_007.entities.Poi;
import com.college.cs4048_group_007.entities.Ride;
import com.college.cs4048_group_007.entities.SaleItem;
import com.college.cs4048_group_007.entities.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    Long insert(Transaction transaction);

    @Query("SELECT * FROM `transaction`")
    LiveData<List<Transaction>> getAllTransactionItem();

    @Query("SELECT * FROM `transaction`")
    LiveData<List<Transaction>> getAllRide();

    @Query("SELECT * FROM `transaction` WHERE transaction_id = :transactionId")
    LiveData<Transaction> getTransactionById(int transactionId);

    @Update
    int updateTransaction(Transaction transaction);

    @Delete
    int deleteTransaction(Transaction transaction);
}
