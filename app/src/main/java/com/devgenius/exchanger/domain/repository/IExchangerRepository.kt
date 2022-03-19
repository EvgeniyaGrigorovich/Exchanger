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
     * Получить список избранных валют
     */
    suspend fun getCurrencyFromLocal(): Flow<List<Rate>>

    /**
     * Сохранить валюту в избранное
     */
    suspend fun addCurrencyToFavourite(rate: Rate)
}