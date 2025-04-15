package com.college.cs4048_group_007.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ride",
        foreignKeys = @ForeignKey(
        entity = Poi.class,               // The parent entity
        parentColumns = "poi_id",          // Column in the poi table
        childColumns = "poi_id",          // Column in the ride table
        onDelete = ForeignKey.CASCADE  ))
public class Ride {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ride_id")
    public int rideId;

    @ColumnInfo(name = "poi_id")
    public int poiId;

    @ColumnInfo(name = "height")
    public float height;

    @ColumnInfo(name = "duration")
    public float duration;

    @ColumnInfo(name = "queue_length")
    public float queueLength;

    @ColumnInfo(name = "rating")
    public String rating;

    public Ride(String rating, int poiId, float height, float duration, float queueLength) {
        this.rating = rating;
        this.poiId = poiId;
        this.height = height;
        this.duration = duration;
        this.queueLength = queueLength;
    }

    public Ride() {

    }
}
