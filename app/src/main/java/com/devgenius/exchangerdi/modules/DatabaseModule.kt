package com.devgenius.exchangerdi.modules

import androidx.room.Room
import com.devgenius.exchanger.data.local.db.RatesDao
import com.devgenius.exchanger.data.local.db.RatesDatabase
import com.devgenius.exchangerdi.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: App) {

    /**
     * Предоставляет экземляр базы данных в граф зафисимостей
     */
    @Provides
    @Singleton
    fun provideRatesDatabase() = Room.databaseBuilder(
        context,
        RatesDatabase::class.java,
        "rates_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    /**
     * Предоставляет [RatesDao] в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideRatesDao(database: RatesDatabase) = database.ratesDao()

}