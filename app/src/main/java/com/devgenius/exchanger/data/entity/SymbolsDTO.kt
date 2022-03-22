package com.devgenius.exchanger.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Модель для получения последнего курса валют
 *
 * @constructor
 * @param success Результат выполнения запроса
 * @param symbols Мапа с наименованием и расшифровкой
 *
 * @author Evgeniia Griorovich
 */
data class SymbolsDTO(

    @SerializedName("success")
    val success: String,

    @SerializedName("symbols")
    val symbols: Map<String, String>
)
