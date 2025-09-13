package com.example.moneymanager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moneymanager.entity.MoneyManager;

import java.util.List;

@Dao
public interface MoneyManagerDao {
    @Insert
    void insert(MoneyManager moneyManager);

    @Update
    void update(MoneyManager moneyManager);

    @Query("SELECT * FROM moneyManager")
    List<MoneyManager> getAll();
}

