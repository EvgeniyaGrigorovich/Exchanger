package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.data.local.db.dbmodel.RateDbModel
import com.devgenius.exchanger.domain.entity.Rate
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal

/**
 * Тест на [RateDbModelToRateConverter]
 */
class RateDbModelToRateConverterTest {
    private val rateDbModelConverter = RateDbModelToRateConverter()

    @Test
    fun `verify convert is success`() = runBlocking {
        val from = flow<List<RateDbModel>> { fromMock }
        val expected = flow<List<Rate>> { toMock }

        val actual = rateDbModelConverter.convert(from = from)

        Truth.assertThat(actual.collect()).isEqualTo(expected.collect())
    }

    companion object {
        val fromMock = listOf(
            RateDbModel(
                id = 1,
                currency = "USD",
                value = 0.11,
                convertedCurrency = "EUR"
            )
        )
        val toMock = listOf(
            Rate(
                currency = "USD",
                value = BigDecimal.valueOf(0.11),
                convertedCurrency = "EUR"
            )
        )
    }
}