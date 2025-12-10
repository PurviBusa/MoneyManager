package com.example.moneymanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void delete(MoneyManager moneyManager);


    @Query("SELECT * FROM moneyManager ORDER BY position ASC")
    List<MoneyManager> getAll();

    @Query("UPDATE moneyManager SET position = :position WHERE id = :id")
    void updatePosition(int id, int position);

    @Query("SELECT COALESCE(MAX(position), -1) FROM moneyManager")
    int getMaxPosition();


}
