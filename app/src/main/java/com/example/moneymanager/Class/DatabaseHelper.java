package com.example.moneymanager.Class;

import androidx.room.Database;

import androidx.room.RoomDatabase;

import com.example.moneymanager.models.MoneyManager;


@Database(entities = MoneyManager.class,exportSchema = false,version = 1)
public abstract class DatabaseHelper extends RoomDatabase  {

    private static final String DB_NAME = "Money_Manager";

    private static DatabaseHelper instance ;


//    public static  synchronized DatabaseHelper getDB(Context context){
//        if (instance==null){
//            instance =Room.databaseBuilder(context,
//                            DatabaseHelper.class,DB_NAME)
//                    .fallbackToDestructiveMigration()
//                    .allowMainThreadQueries()
//                    .build();

//        }
//        return instance;
//
//    }
//    public abstract MoneyMangerDao moneyMangarDao();



}