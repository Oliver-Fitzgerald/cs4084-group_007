package com.college.cs4048_group_007.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.college.cs4048_group_007.entities.SaleItem;
import com.college.cs4048_group_007.entities.Transaction;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepository {
    private final TransactionDao transactionDao;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public TransactionRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        transactionDao = db.transactionDao();
    }

    public void insertTransaction(Transaction transaction) {
        // Running insert operation on a background thread
        executor.execute(() -> transactionDao.insert(transaction));
    }

    public LiveData<Transaction> getTransactionById(int transactionId) {
        // Room automatically runs it on a background thread because DAO returns LiveData<>
        return transactionDao.getTransactionById(transactionId);
    }
    public LiveData<List<Transaction>> getAllRide(){
        return transactionDao.getAllRide();
    }
}
