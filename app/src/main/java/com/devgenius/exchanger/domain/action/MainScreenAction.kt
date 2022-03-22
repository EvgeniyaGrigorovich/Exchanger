package com.devgenius.exchanger.domain.action

import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.presentation.states.SortedState

/**
 * Класс отвечает за действия, производимые над главным экраном
 *
 * @author Evgeniia Grigorovich
 */
sealed class MainScreenAction {

    /**
     * Намерение открыть вкладку с избранными
     *
     * @param sortedState состояние сортировки
     */
    data class OpenFavouritesScreen(
        val sortedState: SortedState
    ) : MainScreenAction()

    /**
     * Намерение открыть вкладку со всеми загруженнымт валютами
     *
     * @param sortedState состояние сортировки
     */
    data class OpenMainScreen(
        val sortedState: SortedState
    ) : MainScreenAction()

    /**
     * Намерение изменить сортировка
     *
     * @param newSortedState желаемое состояние сортировки
     */
    data class ChangeSortedState(
        val newSortedState: SortedState
    ) : MainScreenAction()

    /**
     * Намерение открыть вкладку со всеми загруженнымт валютами
     *
     * @param rate валюта для сохранения
     */
    data class SaveToFavourites(
        val rate: Rate
    ) : MainScreenAction()


    /**
     * Намерение поменять конвертируемую валюту
     *
     * @param currency валюта для сохранения
     */
    data class ChangeCurrency(
        val currency: String
    ) : MainScreenAction()

}