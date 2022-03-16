package com.devgenius.exchanger.data

import com.devgenius.exchanger.BuildConfig
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchangerdi.di.NetworkModule
import dagger.Module
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Api ретрофита для получения валют
 */
@Module(includes = [NetworkModule::class])
interface ExchangeApi {

    /**
     * Создает get зарпос
     */
    @GET("latest")
    suspend fun getCurrency(@Query(BuildConfig.API_KEY_LABEL) string: String): Response<CurrencyDTO>
}