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

  // ✅ No-arg constructor required by Room
  public MoneyManager() {
  }

  // Optional convenience constructor
  public MoneyManager(String name) {
    this.categoryname = name;
  }

  // Getters & setters
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getCategoryname() { return categoryname; }
  public void setCategoryname(String name) { this.categoryname = name; }
}
