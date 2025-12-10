package com.example.moneymanager.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.example.moneymanager.dao.MoneyManagerDao;
import com.example.moneymanager.dao.TransactionDao;
import com.example.moneymanager.entity.MoneyManager;
import com.example.moneymanager.entity.Transaction;

@Database(entities = {MoneyManager.class, Transaction.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)  // Add this
public abstract class MoneyManagerDatabase extends RoomDatabase {

    private static volatile MoneyManagerDatabase INSTANCE;

    public abstract MoneyManagerDao moneyManagerDao();
    public abstract TransactionDao transactionDao();

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