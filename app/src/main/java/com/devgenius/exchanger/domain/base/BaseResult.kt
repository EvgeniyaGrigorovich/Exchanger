package com.devgenius.exchanger.domain.base

import com.devgenius.exchanger.domain.entity.Currency

/**
 * Класс с вариантами состояния загрузки
 *
 * @author Evgeniia Grigorovich
 *
 */
sealed class BaseResult<out T : Any> {
    data class Success<T : Any>(val data: Currency?) : BaseResult<T>()
    object Error : BaseResult<Nothing>()
}
