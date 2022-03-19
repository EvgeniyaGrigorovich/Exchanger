package com.devgenius.exchanger.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import kotlinx.coroutines.flow.Flow

/**
 * Класс dao для работы с базой данных
 *
 * @author Evgeniia Grigorovich
 */
@Dao
interface RatesDao {

    /**
     * Метод для получения списка избранных валют
     */
    @Query("SELECT * FROM rates_table")
    fun getAllRates(): Flow<List<RateDbModel>>

    /**
     * Метод для сохранения валюты в избранной
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRate(rate: RateDbModel)
}