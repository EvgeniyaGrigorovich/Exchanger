package com.devgenius.exchanger.data.repository

import com.devgenius.exchanger.data.storage.IExchangerRemoteStorage
import com.devgenius.exchanger.domain.base.BaseResult
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.repository.IExchangerRepository
import com.devgenius.exchanger.utils.OneWayConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Реализация репозиторя [IExchangerRepository]
 *
 * @author Evgeniya Grigorovich
 */
internal class ExchangerRepository(
    private val remoteStorage: IExchangerRemoteStorage,
    private val currencyConverter: OneWayConverter<CurrencyDTO, Currency>
) : IExchangerRepository {

    override fun getCurrencyFromRemote(): Flow<BaseResult<Currency>> {
        return flow {

            val response = remoteStorage.getCurrencyFromRemote()
            if (response.isSuccessful) {
                val body = response.body()
                val currency = body?.let { currencyConverter.convert(it) }
                emit(BaseResult.Success(currency))
            } else {
                emit(BaseResult.Error)
            }
        }
    }

    override fun getCurrencyFromLocal(): Flow<Currency> {
        TODO("Not yet implemented")
    }

    override fun addCurrencyToFavourite() {
        TODO("Not yet implemented")
    }
}