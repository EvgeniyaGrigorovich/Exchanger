package com.devgenius.exchanger.presentation.converters

import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.utils.OneWayConverter

/**
 * Конвертер из списка [Rate] в строку с наименованием валют
 */
class RatesListToSymbolsConverter : OneWayConverter<List<Rate>, String> {

    override suspend fun convert(from: List<Rate>): String {
        val symbols = StringBuilder()
        for (i in from) {
            symbols.append(i.currency + ",")
        }
        return symbols.toString()
    }
}