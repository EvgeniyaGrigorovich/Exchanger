package com.devgenius.exchanger.domain.usecase

import com.devgenius.exchanger.domain.common.SymbolsResult
import com.devgenius.exchanger.domain.repository.ICurrencySymbolsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Юзкейс для получения наименования всех валют с расшифровкой
 */
class GetAllCurrencySymbolsUseCase @Inject constructor(
    private val symbolsRepository: ICurrencySymbolsRepository
) {

    suspend fun getSymbols(): Flow<SymbolsResult<List<String>>> {
        return symbolsRepository.getAllCurrencySymbols()
    }
}