package com.devgenius.exchanger.utils

/**
 * Маппер из сущности [F] В сущность [T]
 *
 * @param F из
 * @param T в
 *
 * @author Evgeniia Grigorovich
 */
interface OneWayConverter<F, T> {

    /**
     * F -> T
     *
     * @param from из
     * @return в
     */
    suspend fun convert(from: F): T
}