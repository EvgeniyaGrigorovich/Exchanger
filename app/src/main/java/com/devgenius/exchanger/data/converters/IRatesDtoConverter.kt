package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.domain.entity.Rate

/**
 * Конвертер из Map<String, Double> в List<[Rate]>
 *
 * @author Evgeniia Grigorovich
 */
internal interface IRatesDtoConverter {

    /**
     * Конвертирует map в лист [Rate]
     *
     * @param from исходный map
     * @param convertedCurrency валюта исходя из которой приходят значения
     */
    suspend fun convert(from: Map<String, Double>, convertedCurrency: String): List<Rate>
}