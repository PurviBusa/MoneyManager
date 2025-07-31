package com.example.moneymanager.customs;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moneymanager.models.MoneyManager;

import java.util.List;

public interface MoneyMangerDao{

    @Query("select * from moneymanager")
    List<MoneyManager> getAllCatogary();

    @Insert
    void selectCatogary(MoneyManager moneyManager);

    @Update
    void updateCat(MoneyManager moneyManager);

    @Delete

    void deletCat (MoneyManager moneyManager);



}
