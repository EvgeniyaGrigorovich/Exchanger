package com.devgenius.exchanger.presentation.converters

import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.presentation.models.CurrencyViewModel
import com.devgenius.exchanger.presentation.models.RateViewModel
import com.devgenius.exchanger.utils.OneWayConverter

/**
 * Конвертер из [Currency] в [CurrencyViewModel]
 *
 * @param ratesConverter конвертер из [Rate] в [RateViewModel]
 *
 * @author Evgeniia Grigorovich
 */
internal class CurrencyToCurrencyViewModelConverter(
    private val ratesConverter: OneWayConverter<List<Rate>, List<RateViewModel>>
) : OneWayConverter<Currency, CurrencyViewModel> {

    override suspend fun convert(from: Currency): CurrencyViewModel {
        return CurrencyViewModel(success = from.success,
        base = from.base,
        rates = ratesConverter.convert(from.rates))
    }
}