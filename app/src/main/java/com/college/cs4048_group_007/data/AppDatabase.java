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
            poi = new Poi("antique_cars", "A classic car ride for all ages.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("ballons_ride", "A fun hot air balloon-themed ride.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("basgettis", "A restaurant serving delicious Italian cuisine.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("blacksmith_pavilion", "A demonstration of blacksmithing skills.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("blue_goose", "A small merry go round for kids.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("boppers", "A dance floor where you can bust out your dance moves.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("bumper_cars", "Classic bumper car fun for everyone.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("cannon_bowl", "A spinning water ride for thrill-seekers.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("canoes", "A relaxing canoe ride on the lake.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("carousel", "A beautiful and classic carousel.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("catered_picnic_area", "A perfect spot for a catered picnic.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("circus_menagerie", "An exciting collection of circus animals.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("crazy_mouse", "A wild and wacky roller coaster.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("devils_hole", "A spooky and thrilling attraction.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("dinosaur_pool", "A dinosaur-themed water attraction.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("dippin_dots_fat_phils", "A spot to enjoy Dippin' Dots ice cream.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("dippin_dots_snack_bar", "A snack bar with Dippin' Dots ice cream.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("dome", "Live music throughout the day.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("dragster_drench", "A water ride with dragster themes.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("fables", "A storytelling attraction for kids.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("festival_area", "An area for seasonal festivals and events.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("flight", "A thrilling aviation-themed ride.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("fried_dough_dippin_dots", "Enjoy fried dough and Dippin' Dots here.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("full_tilt", "A high-energy tilt-a-whirl ride.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("games", "An area with a variety of games.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("garden_of_fables", "A whimsical garden for storytelling.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("gondola_wheel", "A giant Ferris wheel offering scenic views.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("guest_services", "Guest services for park visitors.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("I_got_it_arcade", "A fun-filled arcade for all ages.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("iron_horse_train", "A scenic train ride around the park.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("jack_and_beanstalk", "Be brought up the beanstalk and get an adrenaline rush as you come back down to earth.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("kidde_train", "A miniature train ride for kids.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("kiddie_boats", "A fun boat ride for young kids.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("koa_entrance", "The main entrance to the park.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("lakeside_pavillion", "A lakeside area for events and relaxation.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("lazy_river", "A relaxing float down the lazy river.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("magic_ring", "An enchanting ride with magical themes.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("martys_finger_lickin_chicken", "Delicious chicken meals served here.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("maxs_doggy_dog_coaster", "A family-friendly roller coaster.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("medical_tent", "Medical assistance available here.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("mega_disko", "A thrilling spinning ride.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("midway_pizza", "Enjoy delicious pizza on the midway.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("midway_theater", "Live shows and entertainment here.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("minature_golf", "A fun miniature golf course for all ages.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("mind_warp", "An intense and thrilling ride.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("miss_kittys_western_shoppe_old_tyme_photo", "Get old-time photos and souvenirs here.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("ragin_rapids", "An exciting water ride through rapids.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("red_baron", "A thrilling airplane-themed ride.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("rock_n_roll", "A music-themed spinning ride.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("ruckin_tug_train_station", "A fun train ride for kids and families.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("silver_commit", "A classic roller coaster experience.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("slide_1_and_2", "Exciting slides for thrill-seekers.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("slide", "A fun slide for all ages.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("surf_hill", "A water slide with surfing themes.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("tea_cups", "A spinning tea cup ride for the whole family.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("tilt-a-whirl", "A classic spinning ride.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("waffle_tower", "Delicious waffles served here.", "9:00", "5:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("water_park_store", "A store with water park essentials.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("wave_pool", "A large pool with exciting waves.", "9:00", "6:00", "entertainment");
            db.poiDao().insert(poi);

            poi = new Poi("western_town", "An old western-themed town for exploration.", "9:00", "8:00", "entertainment");
            db.poiDao().insert(poi);
        }
        Ride ride = new Ride();
        if (db.rideDao().getRideCount() == 0) {
            ride = new Ride("2", 1, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 2, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 5, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 7, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 8, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 9, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 10, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("5", 12, 160, 10, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 13, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 14, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("5", 15, 160, 10, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 19, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 20, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 21, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 22, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("5", 24, 160, 10, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 25, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 26, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 27, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 29, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 30, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 31, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 32, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 33, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 36, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 37, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 39, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("5", 41, 160, 10, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 43, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 44, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 45, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 47, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 48, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 49, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("5", 50, 160, 10, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 51, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 52, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 53, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 54, 180, 5, 0);
            db.rideDao().insert(ride);

            ride = new Ride("5", 55, 160, 10, 0);
            db.rideDao().insert(ride);

            ride = new Ride("2", 56, 100, 30, 0);
            db.rideDao().insert(ride);

            ride = new Ride("3", 59, 180, 5, 0);
            db.rideDao().insert(ride);
        }
        if (db.saleItemDao().getItemCount() < 2) {
            SaleItem saleItem = new SaleItem(1, 20.00F,"ticket", "ticket for one");
            SaleItem saleItem2 = new SaleItem(1, 35.00F,"ticket x2", "ticket for two");
            db.saleItemDao().insert(saleItem);
            db.saleItemDao().insert(saleItem2);
        }
        if (db.saleItemDao().getItemCount() < 3) {
            SaleItem saleItem = new SaleItem(59, 20.00F,"Burger", "non-vegan", 1000);
            db.saleItemDao().insert(saleItem);
        }
        /*if (db.transactionDao().getCount() == 0){
            Transaction transaction = new Transaction(2, "open");
        }*/

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
