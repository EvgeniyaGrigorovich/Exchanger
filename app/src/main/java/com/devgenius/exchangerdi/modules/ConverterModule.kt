package com.devgenius.exchangerdi.modules

import com.devgenius.exchanger.data.converters.*
import com.devgenius.exchanger.data.converters.CurrencyDTOConverter
import com.devgenius.exchanger.data.converters.RateDbModelToRateConverter
import com.devgenius.exchanger.data.converters.RateToRateDbModelConverter
import com.devgenius.exchanger.data.converters.RatesDtoConverter
import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.data.entity.SymbolsDTO
import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.domain.entity.Symbols
import com.devgenius.exchanger.presentation.converters.CurrencyToCurrencyViewModelConverter
import com.devgenius.exchanger.presentation.converters.RatesListToSymbolsConverter
import com.devgenius.exchanger.presentation.converters.RatesToRatesViewModelConverter
import com.devgenius.exchanger.presentation.models.CurrencyViewModel
import com.devgenius.exchanger.utils.OneWayConverter
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow

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
    fun provideCurrencyToCurrencyViewModelConverter(): OneWayConverter<Currency, CurrencyViewModel> {
        val ratesViewModelConverter = RatesToRatesViewModelConverter()
        return CurrencyToCurrencyViewModelConverter(ratesViewModelConverter)
    }

    /**
     * Предотавляет [RateToRateDbModelConverter] в граф зависимостей
     */
    @Provides
    fun provideRateToRateDbModelConverter(): OneWayConverter<Rate, RateDbModel> {
        return RateToRateDbModelConverter()
    }

    /**
     * Предоставляет [RateDbModelToRateConverter] в граф зависимостей
     */
    @Provides
    fun provideRateDbModelToRateConverter(): OneWayConverter<Flow<List<RateDbModel>>, Flow<List<Rate>>> {
        return RateDbModelToRateConverter()
    }

    /**
     * Предоставляет [RatesListToSymbolsConverter] в граф зависимостей
     */
    @Provides
    fun provideRateListToSymbolConverter(): OneWayConverter<List<Rate>, String> {
        return RatesListToSymbolsConverter()
    }

    /**
     * Предоставляет [SymbolsDTOToSymbolsConverter] в граф зависимостей
     */
    @Provides
    fun provideSymbolsDTOToSymbolsConverter(): OneWayConverter<SymbolsDTO, List<String>>{
        return SymbolsDTOToSymbolsConverter()
    }
}