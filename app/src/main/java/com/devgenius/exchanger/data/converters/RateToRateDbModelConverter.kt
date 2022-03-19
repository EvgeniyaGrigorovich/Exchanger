package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.utils.OneWayConverter

/**
 * Конвертер из [Rate] в [RateDbModel]
 *
 * @author Evgeniia Grigorovich
 */
internal class RateToRateDbModelConverter : OneWayConverter<Rate, RateDbModel> {

    override fun convert(from: Rate) =
        RateDbModel(
            currency = from.currency,
            value = from.value
        )
}