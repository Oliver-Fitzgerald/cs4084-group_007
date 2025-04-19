package com.college.cs4048_group_007.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.college.cs4048_group_007.entities.Poi;
import com.college.cs4048_group_007.entities.Ride;
import com.college.cs4048_group_007.entities.SaleItem;
import com.college.cs4048_group_007.entities.Transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * AppDatabase represents database itself and provides access to DAOs
 */
@Database(entities = {Poi.class, Ride.class, SaleItem.class, Transaction.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // Enable Room database debugging
    public static final boolean DEBUG = true;
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
                                    AppDatabase.class, "mobileDatabase.db")
                            .build();
                }
                Log.d("DatabasePath", "Database file: " + context.getDatabasePath("mobileDatabase.db").getAbsolutePath());
            }
        }
        return INSTANCE;
    }

    public static void insertTestData(AppDatabase db) {
        if (db.poiDao().getPoiCount() == 0) {
            Poi poi = new Poi("wave pool", "Take wave poll, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
        }
        if (db.rideDao().getRideCount() == 0) {
            Ride ride = new Ride("5", 1, 20, 100, 30);
            db.rideDao().insert(ride);
        }
    }

    public static void replaceDatabase(Context context, String sourcePath) {
        File dbFile = context.getDatabasePath("mobileDatabase.db");
        try (InputStream inputStream = new FileInputStream(sourcePath);
             OutputStream outputStream = new FileOutputStream(dbFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            Log.d("DatabaseReplace", "Database replaced successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DatabaseReplace", "Failed to replace database: " + e.getMessage());
        }
    }
}
