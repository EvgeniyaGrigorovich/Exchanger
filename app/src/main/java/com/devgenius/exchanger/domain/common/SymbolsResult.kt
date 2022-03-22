package com.devgenius.exchanger.domain.common

import com.devgenius.exchanger.domain.entity.Symbols

/**
 * Класс с вариантами состояния загрузки
 *
 * @author Evgeniia Grigorovich
 *
 */
sealed class SymbolsResult<out T : Any> {
        data class Success<T : Any>(val data: List<String>) : SymbolsResult<T>()
        object Error : SymbolsResult<Nothing>()
}