package com.example.currencyexchangeapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.currencyexchangeapp.data.database.dao.RateDao;
import com.example.currencyexchangeapp.data.database.entities.RateEntity;

/**
 * clase para crear la base de datos usando room
 */

@Database(
        entities = {RateEntity.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    private static final String APP_DATABASE_NAME = "currency_database";

    // vamos a crear las instancias de los dao

    public abstract RateDao rateDao();

    // obtenemos una instancia de esta clase

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, APP_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
