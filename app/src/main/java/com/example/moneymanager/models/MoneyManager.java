package com.example.moneymanager.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")

public class MoneyManager {
//    public MoneyManager(int id, long time) {
//        this.id = id;
//        this.time = time;
//    }

    @PrimaryKey(autoGenerate = true)
    private int id;


//    public long getTime() {
//        return time;
//    }
//
//    public void setTime(long time) {
//        this.time = time;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    long time;

}
