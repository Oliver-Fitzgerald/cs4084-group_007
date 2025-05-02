package com.college.cs4048_group_007.data;

import androidx.room.ColumnInfo;

public class RidePoi {
    @ColumnInfo(name = "ride_id")
    public int rideId;
    @ColumnInfo(name = "poi_id")
    public int poiId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "open_time")
    public String openTime;

    @ColumnInfo(name = "close_time")
    public String closeTime;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "rating")
    public String rating;

    @ColumnInfo(name = "queue_length")
    public String queue_length;
}
