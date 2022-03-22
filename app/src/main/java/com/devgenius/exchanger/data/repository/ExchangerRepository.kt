package com.devgenius.exchanger.data.repository

import android.util.Log
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import com.devgenius.exchanger.data.local.storage.IRatesLocalStorage
import com.devgenius.exchanger.data.storage.IExchangerRemoteStorage
import com.devgenius.exchanger.domain.common.base.BaseResult
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.domain.repository.IExchangerRepository
import com.devgenius.exchanger.utils.OneWayConverter
import kotlinx.coroutines.flow.*

/**
 * Реализация репозиторя [IExchangerRepository]
 *
 * @param remoteStorage сторедж для получения данных по сети
 * @param localStorage сторедж для получения данных из базы данных
 * @param currencyConverter конвертер из [CurrencyDTO] в [Currency]
 * @param rateToRateDbModelConverter конвертер из [Rate] в [RateDbModel]
 * @param rateDbModelToRateConverter конвертер из [RateDbModel] в [Rate]
 *
 * @author Evgeniya Grigorovich
 */
internal class ExchangerRepository(
    private val remoteStorage: IExchangerRemoteStorage,
    private val localStorage: IRatesLocalStorage,
    private val currencyConverter: OneWayConverter<CurrencyDTO, Currency>,
    private val rateToRateDbModelConverter: OneWayConverter<Rate, RateDbModel>,
    private val rateDbModelToRateConverter: OneWayConverter<Flow<List<RateDbModel>>, Flow<List<Rate>>>,
    private val ratesListToSymbolsConverter: OneWayConverter<List<Rate>, String>
) : IExchangerRepository {

    override suspend fun getCurrencyFromRemote(base: String): Flow<BaseResult<Currency>> {
        return flow {
            val response = remoteStorage.getCurrencyFromRemote(
                base = base
            )
            if (response.isSuccessful) {
                val body = response.body()
                val currency = body?.let { currencyConverter.convert(it) }
                emit(BaseResult.Success(currency ?: Currency(false, "", listOf())))
            } else {
                emit(BaseResult.Error)
            }
        }
    }

    override suspend fun getCurrencyFromLocal(): Flow<List<Rate>> {
        return rateDbModelToRateConverter.convert(localStorage.getFavouritesRates())
    }

    override suspend fun addCurrencyToFavourite(rate: Rate) {
        localStorage.saveFavouriteRate(
            rateToRateDbModelConverter.convert(rate)
        )
    }

    override suspend fun getFavouriteCurrencyFromRemote(base: String, list: List<Rate>): Flow<BaseResult<Currency>> {
        return flow {
            val response = remoteStorage.getFavouriteCurrencyFromRemote(
                base = base,
                symbols = ratesListToSymbolsConverter.convert(list)
            )
            if (response.isSuccessful) {
                val body = response.body()
                val currency = body?.let { currencyConverter.convert(it) }
                emit(BaseResult.Success(currency ?: Currency(false, "", listOf())))
            } else {
                emit(BaseResult.Error)
            }
        }
    }
}