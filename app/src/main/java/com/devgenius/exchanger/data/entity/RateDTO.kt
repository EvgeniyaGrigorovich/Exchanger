package com.devgenius.exchanger.data.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

/**
 * Модель курса валюты
 *
 * @constructor
 * @param currency Наименование валюты
 * @param value Цена валюты по отношению к Евро
 *
 * @author Evgeniia Grigorovich
 */
data class RateDTO(

    @SerializedName("currency")
    val currency: String,

    @SerializedName("value")
    val value: Double
)
