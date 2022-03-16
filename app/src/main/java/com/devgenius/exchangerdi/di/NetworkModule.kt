package com.devgenius.exchangerdi.di

import androidx.lifecycle.ViewModelProvider
import com.devgenius.exchanger.BuildConfig
import com.devgenius.exchanger.data.ExchangeApi
import com.devgenius.exchanger.data.repository.ExchangerRepository
import com.devgenius.exchanger.domain.repository.IExchangerRepository
import com.devgenius.exchanger.data.storage.ExchangerRemoteStorage
import com.devgenius.exchanger.data.storage.IExchangerRemoteStorage
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.presentation.MainActivity
import com.devgenius.exchanger.presentation.MainViewModel
import com.devgenius.exchanger.utils.OneWayConverter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    /**
     * Предоставляет okHttpClient в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request =
                    original.newBuilder()
//                        .header(BuildConfig.API_KEY_LABEL, BuildConfig.API_KEY)
                        .build()
                chain.proceed(request)
            }
            .build()
    }

    /**
     * Предоставляет retrofit в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * Предоставляет ExchangeApi в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideExchangeApi(retrofit: Retrofit): ExchangeApi =
        retrofit.create(ExchangeApi::class.java)

    @Provides
    @Singleton
    fun provideExchangerRemoteStorage(exchangeApi: ExchangeApi): IExchangerRemoteStorage =
        ExchangerRemoteStorage(exchangeApi)

    @Provides
    @Singleton
    fun providesExchangerRepository(
        remoteStorage: IExchangerRemoteStorage,
        currencyConverter: OneWayConverter<CurrencyDTO, Currency>
    ): IExchangerRepository =
        ExchangerRepository(remoteStorage, currencyConverter)
}

