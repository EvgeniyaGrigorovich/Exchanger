package com.devgenius.exchanger.data.local.storage

import com.devgenius.exchanger.data.local.db.RatesDao
import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import kotlinx.coroutines.flow.Flow

/**
 * Реализация [IRatesLocalStorage]
 *
 * @param ratesDao класс для работы с базой данных
 *
 * @author Evgeniia Grigorovich
 */
class RatesLocalStorage(
    private val ratesDao: RatesDao
) : IRatesLocalStorage {

    override suspend fun saveFavouriteRate(rate: RateDbModel) {
        ratesDao.saveRate(rate = rate)
    }

    override suspend fun getFavouritesRates(): Flow<List<RateDbModel>> {
        return ratesDao.getAllRates()
    }
}