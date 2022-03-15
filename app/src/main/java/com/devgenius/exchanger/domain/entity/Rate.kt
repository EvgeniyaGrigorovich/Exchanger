package com.devgenius.exchanger.domain.entity

/**
 * Модель валюты
 *
 * @constructor
 * @param currency Наименование валюты
 * @param value Цена валюты по отношению к Евро
 *
 * @author Evgeniia Grigorovich
 */
data class Rate(
    val currency: String,
    val value: Double
)
