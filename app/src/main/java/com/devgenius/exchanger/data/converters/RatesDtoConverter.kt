package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.models.data.RateDTO
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.utils.OneWayConverter

/**
 * Конвертер из [Rate] в [RateDTO]
 *
 * @author Evgeniia Grigorovich
 **/
internal class RatesDtoConverter : OneWayConverter<List<RateDTO>, List<Rate>> {

    override fun convert(from: List<RateDTO>): List<Rate> {
        val resultRates = arrayListOf<Rate>()
        for (rate in from) {
            val newRate = Rate(
                currency = rate.currency,
                value = rate.value
            )
            resultRates.add(newRate)
        }
        return resultRates
    }
}