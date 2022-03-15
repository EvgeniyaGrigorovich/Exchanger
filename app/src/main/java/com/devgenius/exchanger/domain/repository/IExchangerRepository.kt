package com.devgenius.exchanger.domain.repository


import com.devgenius.exchanger.domain.base.BaseResult
import com.devgenius.exchanger.domain.entity.Currency
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
    fun getCurrencyFromRemote(): Flow<BaseResult<Currency>>

    /**
     * Получить список избранных валют
     */
    fun getCurrencyFromLocal(): Flow<Currency>

    /**
     * Сохранить валюту в избранное
     */
    fun addCurrencyToFavourite()
}