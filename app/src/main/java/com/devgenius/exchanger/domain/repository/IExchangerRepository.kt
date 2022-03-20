package com.devgenius.exchanger.domain.repository


import com.devgenius.exchanger.domain.common.base.BaseResult
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.entity.Rate
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для получения и сохранения валют
 *
 * @author Evgeniia Grigorovich
 */
interface IExchangerRepository {

    /**
     * Получить список валют по сети
     */
    suspend fun getCurrencyFromRemote(): Flow<BaseResult<Currency>>

    /**
     * Сохранить валюту в избранное
     */
    suspend fun addCurrencyToFavourite(rate: Rate)

    /**
     *  Получить список избранных валют по сети
     */
    suspend fun getFavouriteCurrencyFromRemote(rateList: List<Rate>): Flow<BaseResult<Currency>>

    /**
     * Получить список избранных валют из базы данных
     */
    suspend fun getCurrencyFromLocal(): Flow<List<Rate>>
}