package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.data.entity.CurrencyDTO
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.entity.Rate
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal
import java.util.Date

/**
 * Тест на [CurrencyDTOConverter]
 */
class CurrencyDTOConverterTest {

    private val ratesDtoConverter: IRatesDtoConverter = mockk() {
       coEvery { convert(testMap, TEST_CONVERTED_CURRENCY) }returns mockList
    }
    private val currencyDTOConverter = CurrencyDTOConverter(ratesDtoConverter)

    @Test
     fun `verify convert is success`() = runBlocking {
        val from = CurrencyDTO(
            success = true,
            timeStamp = 1647949144,
            base = TEST_CONVERTED_CURRENCY,
            date = Date(),
            rates = testMap
        )

        val expected = Currency(
            success = true,
            base = TEST_CONVERTED_CURRENCY,
            rates = mockList
        )

        val actual =  currencyDTOConverter.convert(from)

        Truth.assertThat(actual).isEqualTo(expected)
    }

    companion object {
        private const val TEST_CONVERTED_CURRENCY = "EUR"
        val testMap = mapOf("AED" to 0.11111)
        val mockList = listOf(Rate("AED", BigDecimal.valueOf(0.1111), TEST_CONVERTED_CURRENCY))
    }
}