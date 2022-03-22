package com.devgenius.exchanger.data.repository

import com.devgenius.exchanger.data.entity.SymbolsDTO
import com.devgenius.exchanger.data.storage.ICurrencySymbolsRemoteStorage
import com.devgenius.exchanger.domain.common.SymbolsResult
import com.devgenius.exchanger.domain.repository.ICurrencySymbolsRepository
import com.devgenius.exchanger.utils.OneWayConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Реализация интерфейса [ICurrencySymbolsRepository]
 *
 * @author Evgeniia Grigorovicj
 */
internal class CurrencySymbolsRepository(
    private val remoteStorage: ICurrencySymbolsRemoteStorage,
    private val symbolsDtoConverter: OneWayConverter<SymbolsDTO, List<String>>
) : ICurrencySymbolsRepository {

    override suspend fun getAllCurrencySymbols(): Flow<SymbolsResult<List<String>>> {
        return flow {
            val response = remoteStorage.getCurrencySymbols()
            if (response.isSuccessful) {
                val body = response.body()
                val symbols = body?.let { symbolsDtoConverter.convert(it) }
                emit(SymbolsResult.Success(symbols ?: listOf()))
            } else {
                emit(SymbolsResult.Error)
            }
        }
    }
}