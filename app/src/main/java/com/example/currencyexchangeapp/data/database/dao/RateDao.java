package com.example.currencyexchangeapp.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.currencyexchangeapp.data.database.entities.RateEntity;

import java.util.List;

@Dao
public interface RateDao {

    @Query("SELECT * FROM rates")
    List<RateEntity> getAllRates();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRate(RateEntity rate);

    @Delete
    void deleteRate(RateEntity rate);

    @Query("DELETE FROM rates")
    void deleteAllRates();
}
