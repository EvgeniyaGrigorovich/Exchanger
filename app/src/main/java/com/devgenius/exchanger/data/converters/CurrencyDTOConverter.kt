package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.utils.OneWayConverter

/**
 * Конвертер из [CurrencyDTO] в [Currency]
 *
 * @author Evgeniia Grigorovich
 */
internal class CurrencyDTOConverter(
    private val ratesDtoConverter: OneWayConverter<Map<String, Double>, List<Rate>>
) : OneWayConverter<CurrencyDTO, Currency> {

    override suspend fun convert(from: CurrencyDTO): Currency {
        return Currency(
            success = from.success,
            base = from.base,
            rates = ratesDtoConverter.convert(from.rates)
        )
    }

}