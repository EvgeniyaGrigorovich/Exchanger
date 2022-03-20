package com.devgenius.exchanger.data.storage

import com.devgenius.exchanger.BuildConfig
import com.devgenius.exchanger.data.api.ExchangeApi
import com.devgenius.exchanger.data.entity.CurrencyDTO
import retrofit2.Response

/**
 * Реализация интерфейса [IExchangerRemoteStorage]
 */
internal class ExchangerRemoteStorage(
    private val exchangerApi: ExchangeApi
) : IExchangerRemoteStorage {

    override suspend fun getCurrencyFromRemote(): Response<CurrencyDTO> {
        return exchangerApi.getCurrency(BuildConfig.API_KEY)
    }

    override suspend fun getFavouriteCurrencyFromRemote(symbols: String): Response<CurrencyDTO> {
        return exchangerApi.getFavouriteCurrency(BuildConfig.API_KEY, currency = symbols)
    }
}