package com.devgenius.exchanger.presentation.models

import java.math.BigDecimal

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
    val value: BigDecimal
)