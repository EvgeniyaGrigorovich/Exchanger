package com.devgenius.exchanger.domain.usecase

import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.domain.repository.IExchangerRepository
import javax.inject.Inject

/**
 * Юзкейс удаления валюты из избранного
 *
 * @author Evgeniia Grigorovich
 */
class DeleteFromFavouriteUseCase @Inject constructor(
    private val repository: IExchangerRepository
) {

    suspend fun deleteCurrencyFromLocal(rate: Rate){
        repository.deleteCurrencyFromLocal(rate)
    }
}