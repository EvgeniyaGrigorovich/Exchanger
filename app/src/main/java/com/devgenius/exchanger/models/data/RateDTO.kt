package com.devgenius.exchanger.models.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Модель курса валюты
 *
 * @constructor
 * @param currency Наименование валюты
 * @param value Цена валюты по отношению к Евро
 *
 * @author Evgeniia Grigorovich
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class RateDTO(

    @param:JsonProperty("currency")
    val currency: String,

    @param:JsonProperty("value")
    val value: Double
)
