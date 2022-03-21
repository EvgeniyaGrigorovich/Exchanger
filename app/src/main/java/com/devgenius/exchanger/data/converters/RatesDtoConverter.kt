package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.utils.OneWayConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Конвертер из [RatesDTO] в [List<Rate>]
 *
 * @author Evgeniia Grigorovich
 **/
internal class RatesDtoConverter : OneWayConverter<Map<String, Double>, List<Rate>> {

    override suspend fun convert(from: Map<String, Double>): List<Rate> {
        val resultRates = arrayListOf<Rate>()
        val format = DecimalFormat("#.####")
        format.roundingMode = RoundingMode.CEILING
        for (rate in from) {
            val newRate = Rate(rate.key, format.format(rate.value.toBigDecimal()))
            resultRates.add(newRate)
        }

        return resultRates
    }
}