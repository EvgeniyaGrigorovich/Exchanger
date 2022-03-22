package com.devgenius.exchanger.domain.usecase

import com.devgenius.exchanger.domain.common.BaseResult
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.repository.IExchangerRepository
import com.devgenius.exchanger.domain.entity.Rate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Юзкейс получения валют из избранных
 *
 * @author Evgeniia Grigorovich
 */
class GetFavouriteCurrenciesUseCase @Inject constructor(
    private val repository: IExchangerRepository
) {

    suspend fun getFromLocal(): Flow<List<Rate>> {
        return repository.getCurrencyFromLocal()
    }

    suspend fun getFromRemote(base: String, symbols: List<Rate>): Flow<BaseResult<Currency>> {
        return repository.getFavouriteCurrencyFromRemote(
            base = base,
            symbols = symbols
        )
    }
}