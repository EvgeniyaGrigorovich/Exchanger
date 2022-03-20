package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.utils.OneWayConverter
import kotlinx.coroutines.flow.*

/**
 * Конвертер из [RateDbModel] в [Rate]
 */
internal class RateDbModelToRateConverter :
    OneWayConverter<Flow<List<RateDbModel>>, Flow<List<Rate>>> {

    override suspend fun convert(from: Flow<List<RateDbModel>>): Flow<List<Rate>> =
        from.map { listModels ->
            listModels.map { rateDbModel ->
                Rate(currency = rateDbModel.currency, value = rateDbModel.value)
            }
        }
}