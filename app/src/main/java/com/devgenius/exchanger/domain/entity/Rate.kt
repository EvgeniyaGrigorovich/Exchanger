package com.devgenius.exchanger.domain.entity

import java.math.BigDecimal

/**
 * Модель валюты
 *
 * @constructor
 * @param currency Наименование валюты
 * @param value Цена валюты
 * @param convertedCurrency Валюта по отношению к которой конвертируем
 *
 * @author Evgeniia Grigorovich
 */
data class Rate(
    val currency: String,
    val value: BigDecimal,
    val convertedCurrency: String
)
