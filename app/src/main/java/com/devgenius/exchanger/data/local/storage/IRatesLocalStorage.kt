package com.devgenius.exchanger.data.local.storage

import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import kotlinx.coroutines.flow.Flow

/**
 * Локал сторедж для добавления и получения избранных валют
 *
 * @author Evgeniia Grigorovich
 */
interface IRatesLocalStorage {

    /**
     * Метод для сохранения валюты в избранное
     *
     * @param rate Валюта для сохранения
     */
    suspend fun saveFavouriteRate(rate: RateDbModel)

    /**
     * Метод получения списка избранных валют
     */
    suspend fun getFavouritesRates(): Flow<List<RateDbModel>>

    /**
     * Метод удаления избранных валют
     *
     * @param rate наименование валюты для удаления
     */
    suspend fun deleteFromFavourite(rate: String)

}