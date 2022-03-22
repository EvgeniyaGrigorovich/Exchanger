package com.devgenius.exchanger.data.storage

import com.devgenius.exchanger.data.entity.CurrencyDTO
import retrofit2.Response

/**
 * Сторедж для получения валют из сети
 *
 * @author Evgeniia Grigorovich
 */
interface IExchangerRemoteStorage {

    /**
     * Получить список валют по сети
     *
     * @param base выбранная валюта
     */
    suspend fun getCurrencyFromRemote(base: String) : Response<CurrencyDTO>

    /**
     * Получить список избранных валют по сети
     *
     * @param base выбранная валюта
     * @param symbols список избранных валют
     */
    suspend fun getFavouriteCurrencyFromRemote(base: String, symbols: String): Response<CurrencyDTO>
}