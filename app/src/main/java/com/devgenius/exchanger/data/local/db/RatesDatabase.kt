package com.devgenius.exchanger.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel

/**
 * Класс создания базы данных
 *
 * @author Evgeniia Grigorovich
 */
@Database(entities = [RateDbModel::class], version = 4, exportSchema = true)
abstract class RatesDatabase : RoomDatabase() {

    /**
     * Метод возвращающий dao для работы с базой данных
     */
    abstract fun ratesDao(): RatesDao
}