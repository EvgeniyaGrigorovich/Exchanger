package com.devgenius.exchanger.presentation.states

/**
 * Класс отвечающий за состояние главного экрана
 *
 * @property globalState текущее состояние
 * @property internalState хранит в себе элементы состояния
 *
 * @author Evgeniia Grigorovich
 */
data class MainScreenViewState(
    val globalState: MainScreenGlobalState,
    val internalState: MainScreenInternalState
)

/**
 * Класс с элементами состояния
 *
 * @property isAllCurrenciesShown показывать ли все загруженные валюты
 * @property isFavouritesShown показывать ли только избранные
 * @property isSorted каким способом нужно сортировать элементы
 */
data class MainScreenInternalState(
    val isAllCurrenciesShown: Boolean,
    val isFavouritesShown: Boolean,
    val isSorted: SortedState
)

/**
 * Клаас с текущим состоянием экрана
 */
enum class MainScreenGlobalState {

    /**
     * Загрузка
     */
     LOADING,

    /**
     * Данные загружены
     */
     NORMAL,

    /**
     * Поризошла ошибка
     */
     ERROR,
}

/**
 * Класс с состоянием сортировки
 */
sealed class SortedState {

    /**
     * По умолчанию
     */
    object Default: SortedState()

    /**
     * По алфавиту
     *
     * @property isAscending true - по возрастанию, false - по убыванию
     */
    data class ByAlphabet(val isAscending: Boolean): SortedState()

    /**
     * По Значению
     *
     * @property isAscending true - по возрастанию, false - по убыванию
     */
    data class ByValue(val isAscending: Boolean): SortedState()
}