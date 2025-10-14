package com.example.moneymanager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "moneyManager")
public class MoneyManager {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "categoryname")
    private String categoryname;




    public MoneyManager(String name) {
        this.categoryname = name;
    }

    public MoneyManager(int id, String categoryname) {
        this.id = id;
        this.categoryname = categoryname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String name) {
        this.categoryname = name;
    }
}
