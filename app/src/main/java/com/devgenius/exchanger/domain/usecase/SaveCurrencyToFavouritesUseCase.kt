package com.devgenius.exchanger.domain.usecase

import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.domain.repository.IExchangerRepository
import javax.inject.Inject

/**
 * Юзкейс сохранения валюты в избранное
 *
 * @author Evgeniia Grigorovich
 */
class SaveCurrencyToFavouritesUseCase  @Inject constructor(
    private val repository: IExchangerRepository
) {

    suspend fun invoke(rate: Rate){
        repository.addCurrencyToFavourite(rate)
    }

}