package com.devgenius.exchanger.data

import com.devgenius.exchanger.models.data.CurrencyDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

/**
 * Api ретрофита для получения валют
 */
interface ExchangeApi {

    /**
     * Создает get зарпос
     */
    @GET
    fun getCurrency(): Response<CurrencyDTO>
}