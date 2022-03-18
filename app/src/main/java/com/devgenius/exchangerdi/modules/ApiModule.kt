package com.devgenius.exchangerdi.modules

import com.devgenius.exchanger.data.api.ExchangeApi
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.data.repository.ExchangerRepository
import com.devgenius.exchanger.data.storage.ExchangerRemoteStorage
import com.devgenius.exchanger.data.storage.IExchangerRemoteStorage
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.repository.IExchangerRepository
import com.devgenius.exchanger.utils.OneWayConverter
import dagger.Module
import dagger.Provides
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
        currencyConverter: OneWayConverter<CurrencyDTO, Currency>
    ): IExchangerRepository =
        ExchangerRepository(remoteStorage, currencyConverter)
}