package com.devgenius.exchanger.data.storage

import com.devgenius.exchanger.BuildConfig
import com.devgenius.exchanger.data.api.ExchangeApi
import com.devgenius.exchanger.data.entity.SymbolsDTO
import retrofit2.Response

/**
 * Реализация интерфейса [ICurrencySymbolsRemoteStorage]
 *
 * @author Evgeniia Grigorovich
 */
internal class CurrencySymbolsRemoteStorage(
    private val exchangerApi: ExchangeApi
) : ICurrencySymbolsRemoteStorage {

    override suspend fun getCurrencySymbols(): Response<SymbolsDTO> {
        return exchangerApi.getCurrencySymbols(BuildConfig.API_KEY)
    }
}