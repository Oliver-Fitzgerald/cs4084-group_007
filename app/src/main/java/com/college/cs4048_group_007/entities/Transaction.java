package com.college.cs4048_group_007.entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    public int transactionId;

    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "type")
    public String type;
    @ColumnInfo(name = "status")
    public String status;
}
