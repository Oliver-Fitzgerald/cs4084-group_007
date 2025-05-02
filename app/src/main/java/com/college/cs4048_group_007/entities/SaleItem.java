package com.college.cs4048_group_007.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "sale_item",
        foreignKeys = @ForeignKey(
        entity = Poi.class,               // The parent entity
        parentColumns = "poi_id",          // Column in the poi table
        childColumns = "poi_id",          // Column in the sale_item table
        onDelete = ForeignKey.CASCADE  )
)
public class SaleItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "poi_id")
    public int poiId;

    @ColumnInfo(name = "price")
    public float price;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "timer")
    public long timer;

    public SaleItem () {};

    public SaleItem (int poiId, float price, String name, String description, long timer) {
        this.poiId = poiId;
        this.price = price;
        this.name = name;
        this.description = description;
        this.timer = timer;
    }
}
