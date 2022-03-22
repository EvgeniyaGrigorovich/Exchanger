package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.domain.entity.Rate

internal interface IRatesDtoConverter {

    suspend fun convert(from: Map<String, Double>, convertedCurrency: String): List<Rate>
}