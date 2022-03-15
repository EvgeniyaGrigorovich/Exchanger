package com.devgenius.utils

/**
 * Маппер из сущности [F] В сущность [T]
 *
 * @param F из
 * @param T в
 *
 * @author Evgeniia Grigorovich
 */
internal interface OneWayConverter<F, T> {

    /**
     * F -> T
     *
     * @param from из
     * @return в
     */
    fun convert(from: F): T
}