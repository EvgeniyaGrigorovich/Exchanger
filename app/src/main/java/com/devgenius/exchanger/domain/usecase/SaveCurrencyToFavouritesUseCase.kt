package com.devgenius.exchanger.domain.usecase

import com.devgenius.exchanger.data.repository.IExchangerRepository
import com.devgenius.exchanger.domain.base.BaseResult
import com.devgenius.exchanger.domain.entity.Currency
import kotlinx.coroutines.flow.Flow

/**
 * Юзкейс сохранения валюты в избранное
 *
 * @author Evgeniia Grigorovich
 */
class SaveCurrencyToFavouritesUseCase(
    private val repository: IExchangerRepository
) {

    // TODO suspend fun invoke()

}