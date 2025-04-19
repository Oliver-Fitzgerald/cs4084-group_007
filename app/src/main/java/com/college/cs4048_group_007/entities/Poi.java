package com.college.cs4048_group_007.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "poi")
public class Poi {
    @PrimaryKey(autoGenerate = true)
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

    public Poi() {

    }

    public Poi(String name, String description, String openTime, String closeTime, String type) {
        this.name = name;
        this.description = description;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.type = type;
    }
}
