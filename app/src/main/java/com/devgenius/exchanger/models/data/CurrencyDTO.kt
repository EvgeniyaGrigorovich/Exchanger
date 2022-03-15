package com.devgenius.exchanger.models.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

/**
 * Модель для получения последнего курса валют
 *
 * @constructor
 * @param success Результат выполнения запроса
 * @param timeStamp Время в миллисекундах
 * @param base Валюта к которой
 * @param date Дата запроса
 * @param rates Список валют с ценами
 *
 * @author Evgeniia Griorovich
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrencyDTO(

    @param:JsonProperty("success")
    val success: Boolean,

    @param:JsonProperty("timestamp")
    val timeStamp: Long,

    @param:JsonProperty("base")
    val base: String,

    @param:JsonProperty("date")
    val date: Date,

    @param:JsonProperty("rates")
    val rates: List<RateDTO>

)
