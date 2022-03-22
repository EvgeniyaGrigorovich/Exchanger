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
 * @property isSorted каким способом нужно сортировать элементы
 * @property currency выбранная валюта
 */
data class MainScreenInternalState(
    val isFavouriteScreen: Boolean,
    val isSorted: SortedState,
    val currency: String
)

/**
 * Клаас с текущим состоянием экрана
 */
sealed class MainScreenGlobalState {

    /**
     * Загрузка
     *
     * @param isLoading состояние сазрузки
     */
    data class LOADING(val isLoading: Boolean) : MainScreenGlobalState()


    /**
     * Состояние показа сообщения
     *
     * @param message сообщение ошибки
     */
    data class SHOW_MESSAGE(val message: String) : MainScreenGlobalState()

    /**
     * Начальный стейт
     */
    object INIT : MainScreenGlobalState()
}

/**
 * Класс с состоянием сортировки
 */
sealed class SortedState {

    /**
     * По алфавиту
     *
     * @property isAscending true - по возрастанию, false - по убыванию
     */
    data class ByAlphabet(val isAscending: Boolean) : SortedState()

    /**
     * По Значению
     *
     * @property isAscending true - по возрастанию, false - по убыванию
     */
    data class ByValue(val isAscending: Boolean) : SortedState()
}