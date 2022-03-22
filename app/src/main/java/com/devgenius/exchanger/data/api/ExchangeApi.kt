package com.devgenius.exchanger.data.api

import com.devgenius.exchanger.BuildConfig
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.data.entity.SymbolsDTO
import com.devgenius.exchangerdi.modules.NetworkModule
import dagger.Module
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api ретрофита для получения валют
 */
@Module(includes = [NetworkModule::class])
interface ExchangeApi {

    /**
     * Создает get зарпос для получения списка всех валют
     */
    @GET("latest")
    suspend fun getCurrency(
        @Query(BuildConfig.API_KEY_LABEL) apiKey: String,
        @Query("base") base: String
    ): Response<CurrencyDTO>

    /**
     * Создает get зарпос для получения списка всех валют
     */
    @GET("latest")
    suspend fun getFavouriteCurrency(
        @Query(BuildConfig.API_KEY_LABEL) apiKey: String,
        @Query("base") base: String,
        @Query("symbols" ) currency: String
    ): Response<CurrencyDTO>

    /**
     * Создает get зарпос для получения списка всех наименований валют с расшифровкой
     */
    @GET("symbols")
    suspend fun getCurrencySymbols(
        @Query(BuildConfig.API_KEY_LABEL) apiKey: String,
    ): Response<SymbolsDTO>
}