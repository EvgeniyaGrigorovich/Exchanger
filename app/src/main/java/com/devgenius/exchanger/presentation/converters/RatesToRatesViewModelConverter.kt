package com.devgenius.exchanger.presentation.converters

import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.presentation.models.RateViewModel
import com.devgenius.exchanger.utils.OneWayConverter

/**
 * Конвертер из List<Rates> в List<RatesViewModel>
 *
 * @author Evgeniia Grigorovich
 */
internal class RatesToRatesViewModelConverter : OneWayConverter<List<Rate>, List<RateViewModel>> {

    override fun convert(from: List<Rate>): List<RateViewModel> {
        val resultRatesViewModelList = arrayListOf<RateViewModel>()
        for (rates in from) {
            val newRates = RateViewModel(
                currency = rates.currency,
                value = rates.value
            )
            resultRatesViewModelList.add(newRates)
        }
        return resultRatesViewModelList
    }
}