package com.college.cs4048_group_007.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.college.cs4048_group_007.entities.Poi;
import com.college.cs4048_group_007.entities.Ride;
import com.college.cs4048_group_007.entities.SaleItem;
import com.college.cs4048_group_007.entities.Transaction;

/**
 * AppDatabase represents database itself and provides access to DAOs
 */
@Database(entities = {Poi.class, Ride.class, SaleItem.class, Transaction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract PoiDao poiDao();
    public abstract RideDao rideDao();
    public abstract SaleItemDao saleItemDao();
    public abstract TransactionDao transactionDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "MobileDatabase")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
