package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.utils.OneWayConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Конвертер из [RatesDTO] в [List<Rate>]
 *
 * @author Evgeniia Grigorovich
 **/
internal class RatesDtoConverter : OneWayConverter<Map<String, Double>, List<Rate>> {

    override fun convert(from: Map<String, Double>): List<Rate> {
        val scope = CoroutineScope(Dispatchers.Default)
        val resultRates = arrayListOf<Rate>()
        scope.launch {

            for (rate in from) {
                val newRate = Rate(rate.key, rate.value)
                resultRates.add(newRate)
            }
        }

        return resultRates
    }
}