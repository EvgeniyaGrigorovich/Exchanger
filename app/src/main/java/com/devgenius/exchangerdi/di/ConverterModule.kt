package com.devgenius.exchangerdi.di

import com.devgenius.exchanger.data.converters.CurrencyDTOConverter
import com.devgenius.exchanger.data.converters.RatesDtoConverter
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.presentation.converters.CurrencyToCurrencyViewModelConverter
import com.devgenius.exchanger.presentation.converters.RatesToRatesViewModelConverter
import com.devgenius.exchanger.presentation.models.CurrencyViewModel
import com.devgenius.exchanger.utils.OneWayConverter
import dagger.Module
import dagger.Provides

@Module
class ConverterModule {

    /**
     * Предоставляет [CurrencyDTOConverter] в граф зависимостей
     */
    @Provides
    fun provideCurrencyDTOConverter(): OneWayConverter<CurrencyDTO, Currency> {
        val ratesDtoConverter = RatesDtoConverter()
        return CurrencyDTOConverter(ratesDtoConverter)
    }

    /**
     * Предоставляет [CurrencyToCurrencyViewModelConverter] в граф зависимостей
     */
    @Provides
    fun provideCurrencyToCurrencyViewModelConverter(): OneWayConverter<Currency, CurrencyViewModel>{
        val ratesViewModelConverter = RatesToRatesViewModelConverter()
        return CurrencyToCurrencyViewModelConverter(ratesViewModelConverter)
    }
}