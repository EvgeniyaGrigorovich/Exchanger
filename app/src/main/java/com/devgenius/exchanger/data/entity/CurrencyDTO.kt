package com.devgenius.exchanger.data.entity

import com.google.gson.annotations.SerializedName
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
data class CurrencyDTO(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("timeStamp")
    val timeStamp: Long,

    @SerializedName("base")
    val base: String,

    @SerializedName("date")
    val date: Date,

    @SerializedName("rates")
    val rates: Map<String, Double>

)
