package com.devgenius.exchanger.data.storage

import com.devgenius.exchanger.data.entity.SymbolsDTO
import retrofit2.Response

/**
 * Сторедж для получения наименования валют
 *
 * @author Evgeniia Grigorovich
 */
interface ICurrencySymbolsRemoteStorage {

    /**
     * Получить объект со списком наименований валют и их расшифровкой
     */
    suspend fun getCurrencySymbols(): Response<SymbolsDTO>
}