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


    @ColumnInfo(name = "position")
    private int position;

    public MoneyManager(String name) {
        this.categoryname = name;
    }

    public MoneyManager(int id, String categoryname, int position) {
        this.id = id;
        this.categoryname = categoryname;
        this.position = position;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCategoryname() { return categoryname; }
    public void setCategoryname(String name) { this.categoryname = name; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
}
