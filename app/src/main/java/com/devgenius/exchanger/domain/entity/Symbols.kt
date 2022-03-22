package com.devgenius.exchanger.domain.entity

/**
 * Модель с наименованием валюты и расшифровкой
 *
 * @constructor
 * @param symbol наименование
 * @param decoding расшифровка
 *
 * @author Evgeniia Grigorovich
 */
data class Symbols(
    val symbol: String,
    val decoding: String
)