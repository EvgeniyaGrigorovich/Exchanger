package com.devgenius.exchanger.data

import com.devgenius.exchanger.data.entity.CurrencyDTO
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