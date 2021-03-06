package com.devgenius.exchanger.domain.usecase

import com.devgenius.exchanger.domain.repository.IExchangerRepository
import com.devgenius.exchanger.domain.common.BaseResult
import com.devgenius.exchanger.domain.entity.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Юзкейс получения всех валют
 *
 * @author Evgeniia Grigorovich
 */
class GetAllCurrenciesUseCase @Inject constructor(
    private val repository: IExchangerRepository
) {

    suspend fun invoke(base: String): Flow<BaseResult<Currency>> {
        return repository.getCurrencyFromRemote(base)
    }
}