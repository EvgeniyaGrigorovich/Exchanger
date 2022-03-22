package com.devgenius.exchangerdi.modules

import com.devgenius.exchanger.data.api.ExchangeApi
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.data.entity.SymbolsDTO
import com.devgenius.exchanger.data.local.db.RatesDao
import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import com.devgenius.exchanger.data.local.storage.IRatesLocalStorage
import com.devgenius.exchanger.data.local.storage.RatesLocalStorage
import com.devgenius.exchanger.data.repository.CurrencySymbolsRepository
import com.devgenius.exchanger.data.repository.ExchangerRepository
import com.devgenius.exchanger.data.storage.CurrencySymbolsRemoteStorage
import com.devgenius.exchanger.data.storage.ExchangerRemoteStorage
import com.devgenius.exchanger.data.storage.ICurrencySymbolsRemoteStorage
import com.devgenius.exchanger.data.storage.IExchangerRemoteStorage
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.domain.repository.ICurrencySymbolsRepository
import com.devgenius.exchanger.domain.repository.IExchangerRepository
import com.devgenius.exchanger.utils.OneWayConverter
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class ApiModule {

    /**
     * Предоставляет ExchangeApi в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideExchangeApi(retrofit: Retrofit): ExchangeApi =
        retrofit.create(ExchangeApi::class.java)

    /**
     * Предоставляет [ExchangerRemoteStorage] в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideExchangerRemoteStorage(exchangeApi: ExchangeApi): IExchangerRemoteStorage =
        ExchangerRemoteStorage(exchangeApi)

    /**
     * Предотавляет [ExchangerRepository] в граф зависимостей
     */
    @Provides
    @Singleton
    fun providesExchangerRepository(
        remoteStorage: IExchangerRemoteStorage,
        localStorage: IRatesLocalStorage,
        currencyConverter: OneWayConverter<CurrencyDTO, Currency>,
        rateToRateDbModelConverter: OneWayConverter<Rate, RateDbModel>,
        rateDbModelToRateConverter: OneWayConverter<Flow<List<RateDbModel>>, Flow<List<Rate>>>,
        ratesListToSymbolsConverter: OneWayConverter<List<Rate>, String>
    ): IExchangerRepository =
        ExchangerRepository(
            remoteStorage,
            localStorage,
            currencyConverter,
            rateToRateDbModelConverter,
            rateDbModelToRateConverter,
            ratesListToSymbolsConverter
        )

    /**
     * Предоставляет [RatesLocalStorage] в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideLocalStorage(ratesDao: RatesDao): IRatesLocalStorage {
        return RatesLocalStorage(ratesDao)
    }

    /**
     * Предоставляет [CurrencySymbolsRemoteStorage] в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideSymbolsRemoteStorage(exchangeApi: ExchangeApi): ICurrencySymbolsRemoteStorage {
        return CurrencySymbolsRemoteStorage(exchangeApi)
    }

    /**
     * Предоставляет [CurrencySymbolsRemoteStorage] в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideSymbolsRepository(
        remoteStorage: ICurrencySymbolsRemoteStorage,
        converter: OneWayConverter<SymbolsDTO, List<String>>
    ): ICurrencySymbolsRepository {
        return CurrencySymbolsRepository(remoteStorage, converter)
    }
}