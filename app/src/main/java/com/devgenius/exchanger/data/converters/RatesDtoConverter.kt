package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.domain.entity.Rate
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Конвертер из [Map<String, Double>] в [List<Rate>]
 *
 * @author Evgeniia Grigorovich
 **/
internal class RatesDtoConverter : IRatesDtoConverter {

    override suspend fun convert(from: Map<String, Double>, convertedCurrency: String): List<Rate> {
        val resultRates = arrayListOf<Rate>()
        val format = DecimalFormat("#.####")
        format.roundingMode = RoundingMode.CEILING
        for (rate in from) {
            val newRate = Rate(
                rate.key,
                format.format(rate.value).replace(",", ".").toBigDecimal().abs(),
                convertedCurrency
            )
            resultRates.add(newRate)
        }

        return resultRates
    }
}