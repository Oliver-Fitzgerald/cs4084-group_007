package com.college.cs4048_group_007.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.college.cs4048_group_007.entities.Poi;

import java.util.List;

/**
 * The Data Access Object for Poi class.
 */
@Dao
public interface PoiDao {
    @Insert
    Long insert(Poi poi);

    @Query("SELECT * FROM poi")
    LiveData<List<Poi>> getAllPoi();

    @Query("SELECT * FROM poi WHERE poi_id = :poiId")
    LiveData<Poi> getPoiById(int poiId);

    @Query("SELECT COUNT(*) FROM poi")
    int getPoiCount();

    @Query("SELECT poi_id FROM poi WHERE name = :name")
    LiveData<Integer> getIdByName(String name);

    @Query("SELECT ride.ride_id, poi.poi_id, poi.name, poi.description, poi.open_time, poi.close_time, poi.type, ride.rating, ride.queue_length " +
            "FROM poi inner join ride on poi.poi_id = ride.poi_id WHERE poi.poi_id = :poiId")
    LiveData<RidePoi> getRidePoiById(int poiId);
    @Update
    int updatePoi(Poi poi);

    @Delete
    int deletePoi(Poi Poi);

    @Query("DELETE FROM poi")
    void deleteAllPoi();

}
