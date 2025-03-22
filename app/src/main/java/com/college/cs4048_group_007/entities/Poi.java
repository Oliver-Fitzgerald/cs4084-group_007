package com.college.cs4048_group_007.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "poi")
public class Poi {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "poi_id")
    public String poiId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "open_time")
    public String openDate;

    @ColumnInfo(name = "close_time")
    public String closeTime;

    @ColumnInfo(name = "type")
    public String type;
}
