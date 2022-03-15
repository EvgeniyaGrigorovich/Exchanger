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
     */
    suspend fun getCurrencyFromRemote() : Response<CurrencyDTO>
}