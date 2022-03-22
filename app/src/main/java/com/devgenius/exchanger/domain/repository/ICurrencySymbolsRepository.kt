package com.devgenius.exchanger.domain.repository

import com.devgenius.exchanger.domain.common.SymbolsResult
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для получения названия и расшифровки валют
 *
 * @author Evgeniia Grigorovich
 */
interface ICurrencySymbolsRepository {

    /**
     * Возвращает мап с наименованием валют
     */
    suspend fun getAllCurrencySymbols(): Flow<SymbolsResult<List<String>>>
}