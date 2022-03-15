package com.devgenius.exchanger.domain.usecase

import com.devgenius.exchanger.data.repository.IExchangerRepository
import com.devgenius.exchanger.domain.base.BaseResult
import com.devgenius.exchanger.domain.entity.Currency
import kotlinx.coroutines.flow.Flow

/**
 * Юзкейс получения валют из избранных
 *
 * @author Evgeniia Grigorovich
 */
class GetFavouriteCurrenciesUseCase(
    private val repository: IExchangerRepository
) {

    suspend fun invoke(): Flow<BaseResult<Currency>> {
        return repository.getCurrencyFromRemote()
    }
}