package com.devgenius.exchanger.domain.repository


import com.devgenius.exchanger.domain.common.BaseResult
import com.devgenius.exchanger.domain.entity.Currency
import com.devgenius.exchanger.domain.entity.Rate
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для получения и сохранения валют
 *
 * @author Evgeniia Grigorovich
 */
interface IExchangerRepository {

    /**
     * Получить список валют по сети
     *
     * @param base Валюта по отношению к которй запрашиваем курс
     */
    suspend fun getCurrencyFromRemote(base: String): Flow<BaseResult<Currency>>

    /**
     * Сохранить валюту в избранное
     *
     * @param rate Валюта, которую сохраняяем в избранное
     */
    suspend fun addCurrencyToFavourite(rate: Rate)

    /**
     *  Получить список избранных валют по сети
     *
     * @param base Валюта по отношению к которй запрашиваем курс
     * @param symbols Список избранных валют
     */
    suspend fun getFavouriteCurrencyFromRemote(
        base: String,
        symbols: List<Rate>
    ): Flow<BaseResult<Currency>>

    /**
     * Получить список избранных валют из базы данных
     */
    suspend fun getCurrencyFromLocal(): Flow<List<Rate>>

    /**
     * Удалить валюту из избранного
     */
    suspend fun  deleteCurrencyFromLocal(rate: Rate)
}