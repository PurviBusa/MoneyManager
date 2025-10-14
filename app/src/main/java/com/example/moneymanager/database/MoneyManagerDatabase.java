package com.example.moneymanager.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.moneymanager.dao.MoneyManagerDao;
import com.example.moneymanager.entity.MoneyManager;

@Database(entities = {MoneyManager.class}, version = 1, exportSchema = false)
public abstract class MoneyManagerDatabase extends RoomDatabase {

    private static volatile MoneyManagerDatabase INSTANCE;

    public abstract MoneyManagerDao moneyManagerDao();

    public static MoneyManagerDatabase getDB(Context context) {
        if (INSTANCE == null) {
            synchronized (MoneyManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MoneyManagerDatabase.class, "money_manager_db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
