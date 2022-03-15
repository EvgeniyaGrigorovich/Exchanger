package com.devgenius.exchanger.presentation.models

/**
 * Модель для отображения курса валют на главном экране
 *
 * @constructor
 * @param success Результат выполнения запроса
 * @param base Валюта к которой
 * @param rates Список валют с ценами
 *
 * @author Evgeniia Grigorovich
 */
data class CurrencyViewModel(
    val success: Boolean,
    val base: String,
    val rates: List<RateViewModel>
)