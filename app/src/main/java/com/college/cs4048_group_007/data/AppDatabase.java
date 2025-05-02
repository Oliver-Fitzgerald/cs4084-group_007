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
        Poi poi = new Poi();
        if (db.poiDao().getPoiCount() == 0) {
            poi = new Poi("wave_pool", "Take wave poll, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("western town", "Take western town, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("water park store", "Take water park store, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("waffle tower", "Take waffle tower, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("tilt a whirl", "Take tilt a whirl, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("tea cups", "Take tea cups, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("surf hill", "Take surf hill, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("slide 1 and 2", "Take slide 1 and 2, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("slide", "Take slide, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("silver commit", "Take silver commit, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("ruckin tug train station", "Take ruckin tug, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("rock n roll", "Take rock n roll, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("red baron", "Take red baron, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("ragin_rapids", "Take ragin_rapids, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("miss_kittys_western_shoppe_old_tyme_photo", "Take miss_kittys, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("mind_warp", "Take mind_warp, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("minature_golf", "Take minature_golf, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("midway_theater", "Take midway_theater, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("midway_pizza", "Take midway_pizza, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("mega_disko", "Take mega_disko, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("medical_tent", "Take medical_tent, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("maxs_doggy_dog_coaster", "Take maxs_doggy, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("martys_finger_lickin_chicken", "Take martys_finger, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("magic_ring", "Take magic_ring, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("lazy_river", "Take lazy_river, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("lakeside_pavillion", "Take lakeside, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("koa_entrance", "Take koa_entrance, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("kiddie_boats", "Take kiddie_boats, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("kidde_train", "Take kidde_train, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("jack_and_beanstalk", "Take jack_and_bean, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("iron_horse_train", "Take iron_horse, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("I_got_it_arcade", "Take arcade, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("guest_services", "Take services, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("gondola_wheel", "Take gondola, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("garden_of_fables", "Take garden, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("games", "Take games, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("full-tilt", "Take full, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("fried_dough_dippin_dots", "Take dough, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("flight", "Take flight, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("festival_area", "Take festival_area, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("fables", "Take fables, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("dragster_drench", "Take dragster_drench, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("dome", "Take dome, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("dippin_dots_snack_bar", "Take dippin, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("dippin_dots_fat_phils", "Take phils, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("dinosaur_pool", "Take dinosaur_pool, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("devils_hole", "Take devils_hole, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("crazy_mouse", "Take crazy_mouse, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("circus_menagerie", "Take circus_menagerie, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("catered_picnic_area", "Take picnic, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("carousel", "Take carousel, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("canoes", "Take canoes, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("cannon_bowl", "Take cannon_bowl, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("bumper_cars", "Take bumper_cars, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("boppers", "Take boppers, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("blue_goose", "Take blue_goose, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("blacksmith_pavilion", "Take blacksmith_pavilion, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("basgettis", "Take basgettis, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("ballons_ride", "Take ballons_ride, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
            poi = new Poi("antique_cars", "Take antique_cars, have fun!", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);
        }
        Ride ride = new Ride();
        if (db.rideDao().getRideCount() == 0) {
            ride = new Ride("5", 1, 20, 100, 30);
            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);
//            ride = new Ride("5", 1, 20, 100, 30);
//            db.rideDao().insert(ride);

//            SaleItem saleItem = new SaleItem();
//            if (db.rideDao().getRideCount() == 0) {
//                saleItem = new SaleItem(1, 11, "burger", "tasty burger", 10);
//                db.saleItemDao().insert(saleItem);
//            }
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
