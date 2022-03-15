package com.devgenius.exchanger.presentation.models

/**
 * Модель валюты для отображения на главном экране
 *
 * @constructor
 * @param currency Наименование валюты
 * @param value Цена валюты по отношению к Евро
 *
 * @author Evgeniia Grigorovich
 */
data class RateViewModel(
    val currency: String,
    val value: Double
)