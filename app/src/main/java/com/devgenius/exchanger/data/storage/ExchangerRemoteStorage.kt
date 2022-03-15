package com.devgenius.exchanger.data.storage

import com.devgenius.exchanger.data.ExchangeApi
import com.devgenius.exchanger.models.data.CurrencyDTO
import retrofit2.Response

/**
 * Реализация интерфейса [IExchangerRemoteStorage]
 */
internal class ExchangerRemoteStorage(
    private val exchangerApi: ExchangeApi
) : IExchangerRemoteStorage {

    override suspend fun getCurrencyFromRemote(): Response<CurrencyDTO> {
        return exchangerApi.getCurrency()
    }
}