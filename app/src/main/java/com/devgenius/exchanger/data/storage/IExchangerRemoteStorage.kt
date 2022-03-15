package com.devgenius.exchanger.data.storage

import com.devgenius.exchanger.models.data.CurrencyDTO
import kotlinx.coroutines.flow.Flow
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