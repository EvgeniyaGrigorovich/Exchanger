package com.devgenius.exchanger.domain.usecase

import com.devgenius.exchanger.domain.repository.IExchangerRepository
import com.devgenius.exchanger.domain.entity.Rate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Юзкейс получения валют из избранных
 *
 * @author Evgeniia Grigorovich
 */
class GetFavouriteCurrenciesUseCase  @Inject constructor(
    private val repository: IExchangerRepository
) {

    suspend fun invoke(): Flow<List<Rate>> {
        return repository.getCurrencyFromLocal()
    }
}