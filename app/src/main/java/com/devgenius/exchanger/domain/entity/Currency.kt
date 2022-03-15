package com.devgenius.exchanger.domain.entity

/**
 * Модель с информацией по курсу валют
 *
 * @constructor
 * @param success Результат выполнения запроса
 * @param base Валюта к которой
 * @param rates Список валют с ценами
 *
 * @author Evgeniia Grigorovich
 */
data class Currency(
    val success: Boolean,
    val base: String,
    val rates: List<Rate>
)