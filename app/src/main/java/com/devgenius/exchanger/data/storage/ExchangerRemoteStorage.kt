package com.devgenius.exchanger.data.storage

import com.devgenius.exchanger.BuildConfig
import com.devgenius.exchanger.data.api.ExchangeApi
import com.devgenius.exchanger.data.entity.CurrencyDTO
import retrofit2.Response

/**
 * Реализация интерфейса [IExchangerRemoteStorage]
 *
 * @author Evgeniia Grigorovich
 */
internal class ExchangerRemoteStorage(
    private val exchangerApi: ExchangeApi
) : IExchangerRemoteStorage {

    override suspend fun getCurrencyFromRemote(base: String): Response<CurrencyDTO> {
        return exchangerApi.getCurrency(
            apiKey = BuildConfig.API_KEY,
            base = base
        )
    }

    override suspend fun getFavouriteCurrencyFromRemote(
        base: String,
        symbols: String
    ): Response<CurrencyDTO> {
        return exchangerApi.getFavouriteCurrency(
            apiKey = BuildConfig.API_KEY,
            base = base,
            currency = symbols
        )
    }
}